package com.kingyon.chengxin.insurance.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.insurance.InsuranceErrorCode;
import com.kingyon.chengxin.insurance.api.TkApiService;
import com.kingyon.chengxin.insurance.dto.tkdto.PayUrlDto;
import com.kingyon.chengxin.insurance.util.TkRequestUtil;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.api.PdProductInfoService;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.ProductInfoDetail;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/12 0012.
 */

@DubboService
@Slf4j
public class TkApiServiceImpl implements TkApiService {


    @DubboReference
    private PdProductInfoService productInfoService;

    @DubboReference
    private OmOrderService omOrderService;


    @Override
    public Map<String, Object> validatePolicy(Map<String, Object> params) throws Exception {
        Integer productId = (Integer) params.get("product_id");
        Integer accid = (Integer) params.get("accid");
        String premium = (String) params.get("premium");
        if (accid == null) {
            throw new ServiceException(InsuranceErrorCode.NOT_FOUND_USER_ERROR, "用户ID不能为空");
        }
        ProductInfoDetail productInfoDetail = productInfoService.getProductInfoDetail(productId.longValue());
        String productCode = productInfoDetail.getProductCode();
        params.put("product_id", productCode);
        String toJson = Converter.toJson(params);
        String validatePolicy = TkRequestUtil.requestTk(toJson, "validatePolicy", null);
        //核保成功创建订单
        BigDecimal price = new BigDecimal(premium);
        String order = omOrderService.createOrder(accid.longValue(), productId.longValue(), price, null, Converter.toJson(params));
        return Collections.singletonMap("orderNumber", order);
    }

    @Override
    public Map<String, Object> policy(Long accid, String orderNumber) {
        OrderDto orderDto = omOrderService.checkOrder(accid, orderNumber);
        String detailsOrderNum = orderDto.getDetailsOrderNum();
        String policyInfo = orderDto.getRemark();

        Map params = Converter.parseObject(policyInfo, Map.class);
        params.put("trade_no", detailsOrderNum);
        String policy = TkRequestUtil.requestTk(Converter.toJson(params), "policy", orderNumber);
        Map map = Converter.parseObject(policy, Map.class);
        //保存保单信息到订单明细中
        String insureNum = (String) map.get("policy_no");
        String policyUrl = (String) map.get("policy_url");
        omOrderService.savePolicy(detailsOrderNum, insureNum, policyUrl);
        return map;
    }

    @Override
    public Map<String, Object> generatePayUrl(PayUrlDto payUrlDto) {
        OrderDto orderDto = omOrderService.queryOrder(payUrlDto.getAccid(), payUrlDto.getOrderNumber());
        String payUrl = TkRequestUtil.getPayUrl(payUrlDto, orderDto.getAmount() + "", orderDto.getProductName());
        Map map = Converter.parseObject(payUrl, Map.class);
        String code = (String) map.get("code");
        if (!"200".equals(code)) {
            throw new ServiceException(InsuranceErrorCode.THIRD_ERROR, (String) map.get("err_msg"));
        }
        Map<String, Object> result = (Map<String, Object>) map.get("result");
        String billno = (String) result.get("billno");
        Boolean aBoolean = omOrderService.saveTkTradeNo(orderDto.getOrderNumber(), billno);
        if (aBoolean) {
            return Collections.singletonMap("payUrl", (String) result.get("payUrl"));
        }
        throw new ServiceException(InsuranceErrorCode.THIRD_ERROR, "获取支付链接创建支付号失败");
    }

    @Override
    public Map<String, Object> tkNotify(String requestMsg) {
        Map response = JSONObject.parseObject(requestMsg, Map.class);
        String sign = (String) response.get("sign");  //获取报文中的验签
        String callbackURL = (String) response.get("callbackURL"); //回调地址
        //校验验签是否正确
        response.remove("sign");
        response.remove("callbackURL");
        //排序生成验签
        String sortedStr = JSONObject.toJSONString(response, SerializerFeature.MapSortField);
        String signGenerate = TkRequestUtil.md5(sortedStr + TkRequestUtil.MD5_KEY);
        if (signGenerate.equals(sign)) {
            //解密数据
            String desResponse = TkRequestUtil.decryptAES((String) response.get("biz_content"), TkRequestUtil.AES_KEY);
            Map resParams = JSONObject.parseObject(desResponse, Map.class);
            String billno = (String) resParams.get("billno");
            //修改订单状态
            HashMap<String, Object> params = omOrderService.updatePayStatus(billno);
            //修改订单成功以后掉泰康出单接口出单并保存保单信息
            if (params != null) {
                Long accid = (Long) params.get("accid");
                String orderNumber = (String) params.get("orderNumber");
                policy(accid,orderNumber);
            }

            Map<String,Object > result = new HashMap<>();
                result.put("process_date",new Date());
                result.put("message","回调成功执行");
                result.put("code","200");
                result.put("billno",billno);
            return result;

        } else {
            log.info("泰康验证签名失败");
        }

        return null;
    }


}
