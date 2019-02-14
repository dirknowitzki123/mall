package com.kingyon.chengxin.insurance.service;

import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.framework.net.OkHttpRest;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.framework.util.MD5Util;
import com.kingyon.chengxin.insurance.InsuranceErrorCode;
import com.kingyon.chengxin.insurance.api.QxApiService;
import com.kingyon.chengxin.insurance.dto.qixindto.QxData;
import com.kingyon.chengxin.insurance.dto.qixindto.QxProduct;
import com.kingyon.chengxin.insurance.dto.qxapidto.*;
import com.kingyon.chengxin.insurance.enums.AcActivityStatus;
import com.kingyon.chengxin.insurance.mapper.AcActivityOrderMapper;
import com.kingyon.chengxin.insurance.mapper.AcDiscountMapper;
import com.kingyon.chengxin.insurance.mapper.CmInsuranceConfigMapper;
import com.kingyon.chengxin.insurance.modal.AcActivityOrder;
import com.kingyon.chengxin.insurance.modal.AcDiscount;
import com.kingyon.chengxin.insurance.modal.CmInsuranceConfig;
import com.kingyon.chengxin.insurance.util.CodeGenerateUtils;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@DubboService
@Slf4j
public class QxApiServiceImpl implements QxApiService {

    @Value("${apps.common.qixin.partnerId}")
    private String partnerId;

    @Value("${apps.common.qixin.key}")
    private String key;

    @Value("${apps.common.qixin.url}")
    private String qxUrl;

    @Value("${apps.common.openTest}")
    private String openTest;

    @Value("${apps.common.chengXinOrderCreatedUrl}")
    private String chengXinOrderCreatedUrl;


    @DubboReference
    OmOrderService orderService;

    @DubboReference
    UmUserAccountService userAccountService;


    private static OkHttpRest okHttpRest = OkHttpRest.getHttpClient("qx_client");

    static {
        okHttpRest.addHeader("Content-Type", "application/json;charset=UTF-8");
        okHttpRest.addHeader("Accept", "application/json");
    }

    @Autowired
    private AcActivityOrderMapper activityOrderMapper;

    @Autowired
    private CmInsuranceConfigMapper insuranceConfigMapper;

    @Autowired
    private AcDiscountMapper discountMapper;

    @Override
    public QxData policyDetail(String insureNum) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("insureNum", insureNum);
        Object orderDetail = requestQx(params, "orderDetail");
        String toJson = Converter.toJson(orderDetail);
        QxData qxData = Converter.parseObject(toJson, QxData.class);
        return qxData;
    }

    @Override
    public String getProductDetailUrl(String partnerUniqKey, String caseCode, String paySuccessUrl) throws Exception {
        String signStr = key + partnerId;
        String sign = MD5Util.MD5Encode(signStr, "UTF-8");

        if (!StringUtils.isEmpty(paySuccessUrl)) {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] textByte = paySuccessUrl.getBytes("UTF-8");
            paySuccessUrl = encoder.encodeToString(textByte);
        }
        String url = qxUrl + "dispatch/cps?partnerId=" + partnerId + "&subPartnerId=&partnerUniqKey=" + partnerUniqKey + "&caseCode=" + caseCode + "&sign=" + sign + "&paySuccess=" + paySuccessUrl;
        return url;
    }

    @Override
    public Object defaultTrial(String caseCode) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        Map trial = (Map) requestQx(params, "defaultTrial");
        Integer iprice = (Integer) trial.get("price");
        BigDecimal price = new BigDecimal(iprice);
        price = price.divide(new BigDecimal("100"),BigDecimal.ROUND_HALF_UP);
        trial.put("price",price);
        return trial;
    }

    @Override
    public Object orderTrial(OrderTrialDto orderTrialDto) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", orderTrialDto.getStartDate());
        params.put("caseCode", orderTrialDto.getCaseCode());
        params.put("newRestrictGenes", orderTrialDto.getNewRestrictGenes());
        params.put("oldRestrictGene", orderTrialDto.getOldRestrictGene());
        Map orderTrial = (Map)requestQx(params, "orderTrial");
        Integer iprice = (Integer) orderTrial.get("price");
        BigDecimal price = new BigDecimal(iprice);
        price = price.divide(new BigDecimal("100"),BigDecimal.ROUND_HALF_UP);
        orderTrial.put("price",price);
        return orderTrial;
    }

    @Override
    public String insure(InsureReq insureReq) throws Exception {

        AcActivityOrder acActivityOrder = activityOrderMapper.selectByBargainNum(insureReq.getBargainNum());
        if (null == acActivityOrder) {throw new ServiceException(InsuranceErrorCode.NOT_FOUND_BARGAIN_NUM);}
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", insureReq.getCaseCode());
        params.put("startDate", insureReq.getStartDate());
        params.put("applicant", insureReq.getApplicant());
        params.put("insurants", insureReq.getInsurants());
        params.put("priceArgs", insureReq.getPriceArgs());
        params.put("otherInfo", insureReq.getOtherInfo());
        params.put("partnerUniqKey", insureReq.getPartnerUniqKey());
        HashMap insure = (HashMap) requestQx(params, "insure");

        acActivityOrder.setStatus(AcActivityStatus.PAYING.getCode());
        acActivityOrder.setInsureNum((String) insure.get("insureNum"));
        int i = activityOrderMapper.updateByPrimaryKey(acActivityOrder);

        return i > 0 ? acActivityOrder.getBargainNum() : "投保失败";
    }

    @Override
    public Map<String, Object> insureCommit(InsureReq insureReq) throws Exception {

        if ( null == insureReq.getAccid() ) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_USER_ERROR,"用户ID不能为null");
        }
        UserAccountDto userAccountDto = userAccountService.selectByPrimaryKey(insureReq.getAccid());
        if (userAccountDto == null) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_USER_ERROR);
        }

        List<Insurants> insurants = insureReq.getInsurants();

        BigDecimal singlePrice = null;
        if (insurants != null && insurants.size() > 0) {
            singlePrice = insurants.get(0).getSinglePrice();
            BigDecimal multiply = singlePrice.multiply(new BigDecimal(100));
            insurants.get(0).setSinglePrice(multiply);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", insureReq.getCaseCode());
        params.put("startDate", insureReq.getStartDate());
        if (null != insureReq.getEndDate()) {
            params.put("endDate", insureReq.getEndDate());
        }
        params.put("applicant", insureReq.getApplicant());
        params.put("insurants", insureReq.getInsurants());
        params.put("priceArgs", insureReq.getPriceArgs());
        params.put("otherInfo", insureReq.getOtherInfo());
        params.put("partnerUniqKey", insureReq.getAccid());
        HashMap insure = (HashMap) requestQx(params, "insure");
        String insureNum = (String) insure.get("insureNum");
        String orderNum = "";
        Map<String, Object> resulst = new HashMap<>();
        //保单提交成功后 保存订单信息
        if (null != insureNum) {
            Long accid = insureReq.getAccid();
            log.info("实际支付金额: " + singlePrice);
            //测试环境支付金额为1分钱.
            BigDecimal payPrice = "true".equals(openTest) ? new BigDecimal("0.01") : singlePrice;
            orderNum = orderService.createOrder(accid, insureReq.getProductId(), payPrice, insureNum,null);
            resulst.put("orderNum", orderNum);
            resulst.put("accid", accid);

        }
        return resulst;
    }

    @Override
    public HashMap<String, Object> productInsuredArea(String caseCode, String areaCode) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        params.put("areaCode", areaCode);
        HashMap productInsuredArea = (HashMap) requestQx(params, "productInsuredArea");
//        Object areasMap = productInsuredArea.get("areas");
//
//        String toJson = Converter.toJson(areasMap);
//        List list = Converter.parseObject(toJson, List.class);
//        List<Areas> result = new ArrayList<>();
//        for (Object obj : list) {
//            String areasJson = Converter.toJson(obj);
//            Areas areas = Converter.parseObject(areasJson, Areas.class);
//            if (list.size() > 0) {
//                Thread.sleep(800l);
//                List<Areas> areasList = productInsuredArea(caseCode, areas.getAreaCode());
//                areas.setChildren(areasList);
//            }
//            result.add(areas);
//        }
//
//        String s = Converter.toJson(result);
//        log.info(s);
        return productInsuredArea;
    }

    @Override
    public Object productInsuredJob(String caseCode) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        Object productInsuredJob = requestQx(params, "productInsuredJob");
        String toJson = Converter.toJson(productInsuredJob);
        QxData data = Converter.parseObject(toJson, QxData.class);

        List<ProductInsuredJob> jobs = data.getJobs();
        Map<String, ProductInsuredJob> dtoMap = new HashMap<>();
        for (ProductInsuredJob job : jobs) {
            dtoMap.put(job.getId(), job);
        }
        List<ProductInsuredJob> resultList = new ArrayList<>();
        for (Map.Entry<String, ProductInsuredJob> entry : dtoMap.entrySet()) {
            ProductInsuredJob node = entry.getValue();
            if ("0".equals(node.getParentId())) {
                resultList.add(node);
            } else {
                if (dtoMap.get(node.getParentId()) != null) {
                    dtoMap.get(node.getParentId()).getChildren().add(node);
                }
            }
        }
        return resultList;
    }

    @Override
    public Object productDetail(String caseCode) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        Object productDetail = requestQx(params, "productDetail");
        return productDetail;
    }

    @Override
    public Object healthStatement(HealthStatementReq healthStatementReq) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", healthStatementReq.getCaseCode());
        params.put("genes", healthStatementReq.getGenes());
        Object healthStatement = requestQx(params, "healthStatement");
        return healthStatement;
    }

    @Override
    @Deprecated
    public Object productInsuredAttr(Long productId, String partnerUniqKey, String shareCode) throws Exception {
        UserAccountDto userAccountDto = userAccountService.selectByOpenId(partnerUniqKey);
        if (null == userAccountDto) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_USER_ERROR);
        }
        if (null == userAccountDto.getPhone()) {
            throw new ServiceException(InsuranceErrorCode.NO_BING_PHONE);
        }

        CmInsuranceConfig insuranceConfig = insuranceConfigMapper.selectPutawayByProductId(productId);
        if (insuranceConfig == null) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_PRODUCT);
        }
        String caseCode = insuranceConfig.getAddress();

        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        Map productInsureAttrResp = (Map) requestQx(params, "productInsuredAttr");
        HashMap insureAttribute = (HashMap) productInsureAttrResp.get("insureAttribute");
        String productName = (String) insureAttribute.get("productName");
        //获取产品单价
        HashMap productDetail = (HashMap) productDetail(caseCode);
        Integer price = (Integer) productDetail.get("price");

        AcActivityOrder acActivityOrder = new AcActivityOrder();
        //保存活动订单信息
        acActivityOrder.setProductId(productId.toString());
        String bargainNum = "CX_AC_"+CodeGenerateUtils.generateUnionPaySn();
        acActivityOrder.setBargainNum(bargainNum);
        acActivityOrder.setProductName(productName);
        acActivityOrder.setProductSubName(productName);
        acActivityOrder.setMarketPrice(price);
        acActivityOrder.setOpenId(partnerUniqKey);
        acActivityOrder.setShareCode(shareCode);
        acActivityOrder.setStatus(AcActivityStatus.WAIT_PAY.getCode());
        //查询商品是否参加了活动
        AcDiscount discount = discountMapper.selectByProductId(productId);
        if (null != discount) {
            if (discount.getState() == -1) {
                throw new ServiceException(InsuranceErrorCode.NOT_FOUND_PRODUCT, "活动已关闭");
            }
            if (discount.getAstrict() == 1) {
                int i = activityOrderMapper.selectByOpenIdAndProid(productId + "", partnerUniqKey, AcActivityStatus.PAYED.getCode());
                if (i > 0) {
                    throw new ServiceException(InsuranceErrorCode.NOT_FOUND_USER_ERROR, "每人只能购买一次");
                }
            }
            acActivityOrder.setAcCode(discount.getAcCode());
            if (discount.getDiscount() == 0) {
                acActivityOrder.setPrice(0);
            } else {
                int marketPrice = price * discount.getDiscount() / 100;
                acActivityOrder.setPrice(marketPrice);
            }
            activityOrderMapper.insert(acActivityOrder);
        } else {
            acActivityOrder.setPrice(price);
            activityOrderMapper.insert(acActivityOrder);
        }

        productInsureAttrResp.put("singlePrice", price);
        productInsureAttrResp.put("bargainNum", bargainNum);

        return productInsureAttrResp;
    }

    @Override
    public Object productInsuredAttr(String caseCode) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", caseCode);
        Map productInsureAttrResp = (Map) requestQx(params, "productInsuredAttr");

        return productInsureAttrResp;
    }


    @Override
    public Object localPay(String bargainNum) throws Exception {
        AcActivityOrder acActivityOrder = activityOrderMapper.selectByBargainNum(bargainNum);
        Object localPayRes = null;
        try {
            if (null == acActivityOrder){ throw new ServiceException(InsuranceErrorCode.NOT_FOUND_BARGAIN_NUM);}
            String insureNum = acActivityOrder.getInsureNum();
            Integer marketPrice = "true".equals(openTest) ? 1 : acActivityOrder.getMarketPrice();
            Map<String, Object> params = new HashMap<>();
            params.put("insureNums", insureNum);
            params.put("money", marketPrice);
            localPayRes = requestQx(params, "localPay");
            //修改活动订单状态.
            acActivityOrder.setStatus(AcActivityStatus.PAYED.getCode());
            activityOrderMapper.updateByPrimaryKey(acActivityOrder);

            new Thread(() -> {
                //======================================生成0元订单=============================================
                OkHttpRest okHttpRest1 = OkHttpRest.getHttpClient("order_client");
                Map<String, Object> orderParams = new HashMap<>();
                orderParams.put("projectId", acActivityOrder.getProductId());
                orderParams.put("projectType", 2);
                orderParams.put("userOPenId", acActivityOrder.getOpenId());
                okHttpRest1.addHeader("version", "1.0");
                okHttpRest1.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                String res = okHttpRest1.doPost(chengXinOrderCreatedUrl, orderParams, String.class);
                log.info("零元订单生成返回信息 : " + res);
                //===============================================================================================
            }).start();
        } catch (ServiceException e) {
            log.error("慧择支付失败 : 活动订单号:{} ", bargainNum);
            acActivityOrder.setStatus(AcActivityStatus.FAIL.getCode());
            activityOrderMapper.updateByPrimaryKey(acActivityOrder);
            throw e;
        }

        return localPayRes;
    }

    @Override
    public Object localOrderPay(Long accid, String orderNumber) throws Exception {
        Object localPayRes = null;
        OrderDto orderDto = orderService.checkOrder(accid, orderNumber);
        if (orderDto == null) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_BARGAIN_NUM);
        }
        if (!orderDto.getPayStatus().equals(PayStatusEnum.PAID.getStatus())) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_BARGAIN_NUM, "订单未支付,请先支付");
        }
        String insureNum = orderDto.getInsureNum();
        Integer price = orderDto.getPrice().multiply(new BigDecimal(100)).intValue();
        try {
            Integer marketPrice = "true".equals(openTest) ? 1 : price;
            Map<String, Object> params = new HashMap<>();
            params.put("insureNums", insureNum);
            params.put("money", marketPrice);
            localPayRes = requestQx(params, "localPay");
        } catch (ServiceException e) {
            log.error("慧择支付失败--订单号:{} ", orderNumber);
            throw e;
        }
        return localPayRes;
    }


    @Override
    public Object submitHealthState(SubmitHealthStateReq submitHealthStateReq) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("caseCode", submitHealthStateReq.getCaseCode());
        params.put("genes", submitHealthStateReq.getGenes());
        params.put("qaAnswer", submitHealthStateReq.getQaAnswer());
        Object submitHealthState = requestQx(params, "submitHealthState");
        return submitHealthState;
    }

    @Override
    public Object downloadUrl(String insureNum) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("insureNum", insureNum);
        Object downloadUrl = requestQx(params, "downloadUrl");
        return downloadUrl;
    }

    @Override
    public List<QxProduct> productList() throws Exception {
        Map<String, Object> params = new HashMap<>();
        Object response = requestQx(params, "productList");
        String toJson = Converter.toJson(response);
        QxData qxData = Converter.parseObject(toJson, QxData.class);

        log.info("产品列表 : " + qxData);
        List<QxProduct> products = new ArrayList<>();
        products = qxData.getProducts();
        return products;
    }

    private Object requestQx(Map<String, Object> params, String apiName) {

        String tranNo = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        params.put("partnerId", Integer.parseInt(partnerId));
        params.put("transNo", tranNo);

        String paramsJson = Converter.toJson(params, false, true);
        paramsJson = paramsJson.replaceAll("cname", "cName");

        String signStr = key + paramsJson;
        log.info("慧择入参签名-{} : {}", apiName, signStr);
        String sign = MD5Util.MD5Encode(signStr, "UTF-8");
        String url = qxUrl + "api/" + apiName + "?sign=" + sign;
        Map<String, Object> result = okHttpRest.doPost(url, paramsJson, Map.class);

        if (result.get("respCode") != null && (int) result.get("respCode") != 0) {
            String respMsg = (String) result.get("respMsg");
            log.error("慧择入参签名-{} : {}", apiName, signStr);
            log.error("慧择请求错误信息 : " + respMsg);
            throw new ServiceException(InsuranceErrorCode.THIRD_ERROR, respMsg);
        }

        return result.get("data");
    }



//    public static void main(String[] args) {
//
////        String accesstoken = "14_ac25ov8CtKZDdXpsC41W7nYsXDCds_HmJMLGq6rM0B0AZh_FQokYnzdj5u1NmeW_MybE94CViANdePD2Z98iCOazwQg2xazfMXcT1N1kW_0cbf1_MsF1Y-Hb-hPbQKnGu2eOsICeGdNXzLCtCMWcACAMWG";
////        String add = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
////       String dele = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
////        String replace = add.replace("ACCESS_TOKEN", accesstoken);
////        HashMap params = new HashMap();
////        HashMap req = new HashMap();
////        List list = new ArrayList();
////        params.put("type", "view");
////        params.put("name", "惟民测试");
////        params.put("url", "http://test-wm.boyacx.com/home");
////        list.add(params);
////        req.put("button", list);
////
////        String toJson = Converter.toJson(req);
////        String s = okHttpRest.doPost(replace, toJson, String.class);
////
//////        String s = okHttpRest.doGet(replace, String.class);
////        System.out.println(s);
//
//    }


//    public static void main(String[] args) {
//        OkHttpRest okHttpRest1 = OkHttpRest.getHttpClient("order_client");
//        String url="http://localhost:8080/rest/payment/wxPay/insureOrderCreated";
//        Map<String,Object> orderParams = new HashMap<>();
//        orderParams.put("projectId",202);
//        orderParams.put("projectType",2);
//        orderParams.put("userOPenId","oe-u41Vbg_cE2UapdCcQ7XwAuZZU");
//
//        okHttpRest1.addHeader("version","1.0");
//        okHttpRest1.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        Map res = okHttpRest1.doPost(url, orderParams, Map.class);
//        System.out.println(res);
//
//
//    }


}
