package com.kingyon.chengxin.product.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderService;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.query.OrderExportQuery;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.CreateOrderDto;
import com.kingyon.chengxin.product.dto.request.OrderRefund;
import com.kingyon.chengxin.product.enums.OrderStatusEnum;
import com.kingyon.chengxin.product.enums.PayStatusEnum;
import com.kingyon.chengxin.product.enums.PayWayEnum;
import com.kingyon.chengxin.product.enums.ProductType;
import com.kingyon.chengxin.product.mapper.*;
import com.kingyon.chengxin.product.modal.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: Aspen
 * @Date: 2018/10/23 0023 16:56
 */

@DubboService
@Slf4j
public class OmOrderServiceImpl implements OmOrderService {


    private static final Integer BUY_NUM = 1;

    @Autowired
    OmOrderMapper orderMapper;

    @Autowired
    OmOrderDetailsMapper omOrderDetailsMapper;

    @Autowired
    PdProductInfoMapper productInfoMapper;

    @Autowired
    OmOrderRefundMapper orderRefundMapper;

    @Autowired
    OmOrderStatusRecMapper omOrderStatusRecMapper;

    @Autowired
    PdSkuMapper skuMapper;

    @Autowired
    CmSupplierDictionariesMapper supplierDictionariesMapper;

    @Autowired
    SysCodeInfoMapper codeInfoMapper;


    @Override
    @Transactional
    public String createOrder(Long accid, Long productId, BigDecimal price, String insureNum, String remark) {
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(productId);
        if (pdProductInfo == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT);
        }
        if (!ProductType.INSURANCE.getCode().equals(pdProductInfo.getProType()) && price.compareTo(pdProductInfo.getPrice()) != 0) {
            throw new ServiceException(ProductErrorCode.PRICE_ERROR);
        }

        Date currentDate = new Date();
        String orderNum = generatorOrderNum();
        //保存订单
        OmOrder order = new OmOrder();
        order.setAmount(price);
        order.setBuyAccount(accid);
        order.setOrderNumber(orderNum);
        order.setOrderTime(currentDate);
        order.setPayStatus(PayStatusEnum.UNPAID.getStatus());
        order.setStatus(OrderStatusEnum.UNPAY.getStatus());
        order.setRemark(remark);
        int insert = orderMapper.insert(order);
        //保存订单详情
        String mainImage = pdProductInfo.getMainImage();
        if (null != mainImage) {
            String[] split = mainImage.split(",");
            mainImage = split[0];
        }
        OmOrderDetails orderDetails = new OmOrderDetails();
        orderDetails.setOrderId(order.getId());
        orderDetails.setDetailsOrderId(orderNum);
        orderDetails.setMainImage(mainImage);
        orderDetails.setComboProjectId(productId);
        orderDetails.setProductName(pdProductInfo.getProductName());
        orderDetails.setSubtitle(pdProductInfo.getSubtitle());
        orderDetails.setBuyNum(BUY_NUM);
        orderDetails.setProjectType(pdProductInfo.getProType());
        orderDetails.setCreateTime(currentDate);
        orderDetails.setInsureNum(insureNum);
        orderDetails.setPrice(price);
        orderDetails.setChannelId(pdProductInfo.getChannelId());
        int i = omOrderDetailsMapper.insert(orderDetails);
        if (i > 0) {
            return orderNum;
        }
        throw new ServiceException(ProductErrorCode.SAVE_FAIL, "创建订单失败");

    }

    @Override
    public Map<String, Object> queryOrder(Long accid, String orderNum) {
        Map<String, Object> order = orderMapper.queryOrderByOrderNum(accid, orderNum, PayStatusEnum.UNPAID.getStatus());
        if (order == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        return order;
    }

    @Override
    public Map<String, Object> checkOrder(Long accid, String orderNum) {
        log.info("检查order是否支付成功: {}", orderNum);
        Map<String, Object> order =orderMapper.queryOrderByOrderNum(accid, orderNum, PayStatusEnum.PAID.getStatus());
        if (order == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        return order;
    }

    @Override
    public PageDto orderList(OrderQuery orderQuery) {
        PageHelper.startPage(orderQuery.getPageIndex(), orderQuery.getPageSize());
        List<Map<String, Object>> orderList = orderMapper.selectOrderList(orderQuery);
        PageInfo pageInfo = new PageInfo(orderList);
        PageDto pageDto = new PageDto();
        pageDto.setDataCount(Integer.parseInt(pageInfo.getTotal() + ""));
        pageDto.setPageIndex(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setData(orderList);
        return pageDto;
    }

    @Override
    public PageDto orderListExport(OrderExportQuery orderExportQuery) {
        OrderQuery orderQuery = new OrderQuery();
        BeanUtils.copyProperties(orderExportQuery, orderQuery);
        List<Map<String, Object>> orderList = orderMapper.selectOrderList(orderQuery);
        PageDto pageDto = new PageDto();
        pageDto.setData(orderList);
        return pageDto;
    }

    @Override
    public Map<String, Object> orderDetail(String orderNumber) throws Exception {
        Map<String, Object> orderDetail = orderMapper.orderDetail(orderNumber);
        if (orderDetail == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        orderDetail.put("orderStatusDate", omOrderStatusRecMapper.selectList(orderNumber));
        return orderDetail;
    }

    @Override
    public Boolean orderDetailRemark(String orderNumber, String remark) {
        OmOrderDetails orderDetails = omOrderDetailsMapper.selectByOrderNumber(orderNumber);
        if (orderDetails == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        orderDetails.setRemark(remark);
        orderDetails.setModifyTime(new Date());
        int i = omOrderDetailsMapper.updateByPrimaryKeySelective(orderDetails);
        return i > 0;
    }

    @Override
    @Transactional
    public Boolean offlineRefund(OrderRefund orderRefund) {
        Map<String, Object> order = orderMapper.orderDetail(orderRefund.getOrderNumber());
        if (order == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
        }
        Object payStatus = (Integer) order.get("payStatus");
        if (!PayStatusEnum.PAID.getStatus().equals(payStatus)) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER, "订单未支付不能发起退款");
        }

        Date date = new Date();
        //保存退款原因
        OmOrderDetails orderDetails = omOrderDetailsMapper.selectByOrderNumber(orderRefund.getOrderNumber());
        OmOrderRefund omOrderRefund = orderRefund.copyTo(OmOrderRefund.class);
        omOrderRefund.setOrderDetailId(orderDetails.getId().intValue());
        omOrderRefund.setRefundTime(date);
        omOrderRefund.setDeleted((byte) 0);
        orderRefundMapper.insert(omOrderRefund);
        Long orderId = (Long) order.get("id");

        //修改订单为已退款状态
        OmOrder omOrder = new OmOrder();
        omOrder.setId(orderId);
        omOrder.setPayStatus(PayStatusEnum.REFUNDED.getStatus());
        omOrder.setModifyTime(date);
        int i = orderMapper.updateByPrimaryKeySelective(omOrder);
        return i > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createMallOrder(CreateOrderDto createOrderDto) {
        //查询产品信息
        PdProductInfo pdProductInfo = productInfoMapper.selectByPrimaryKey(createOrderDto.getProductId());
        if (pdProductInfo == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_PRODUCT);
        }
        if (!ProductType.ENTITY.getCode().equals(pdProductInfo.getProType())){
            throw new ServiceException(ProductErrorCode.PRODUCT_TYPE_NOT_MATCH);
        }

        //查询Sku信息
        PdSku pdSku = skuMapper.selectByPrimaryKey(createOrderDto.getSkuId());
        if (pdSku == null) {
            throw new ServiceException(ProductErrorCode.NOT_FOUND_SKU);
        }
        if (createOrderDto.getPrice().compareTo(pdSku.getMarketPrice()) != 0) {
            throw new ServiceException(ProductErrorCode.PRICE_ERROR);
        }
        if (pdSku.getStock() <= 0){
            throw new ServiceException(ProductErrorCode.NOT_ENOUGH_SKU);
        }

        //生成订单编号
        String orderNum = generatorOrderNum();
        //保存订单
        OmOrder order = new OmOrder();
        order.setAmount(createOrderDto.getPrice().multiply(new BigDecimal(createOrderDto.getBuyNum())));
        order.setBuyAccount(createOrderDto.getAccid());
        order.setOrderNumber(orderNum);
        order.setOrderTime(new Date());
        order.setPayStatus(PayStatusEnum.UNPAID.getStatus());
        order.setStatus(OrderStatusEnum.ORDER_UNPAIED.getStatus());
        order.setRemark(createOrderDto.getRemark());
        int insertOrder = orderMapper.insert(order);

        //保存订单详情
        OmOrderDetails orderDetails = new OmOrderDetails();
        orderDetails.setOrderId(order.getId());
        orderDetails.setDetailsOrderId(orderNum);
        orderDetails.setMainImage(pdSku.getSkuMainPic());
        orderDetails.setComboProjectId(createOrderDto.getProductId());
        orderDetails.setProductName(pdProductInfo.getProductName());
        orderDetails.setSubtitle(pdProductInfo.getSubtitle());
        orderDetails.setBuyNum(createOrderDto.getBuyNum());
        orderDetails.setProjectType(pdProductInfo.getProType());
        orderDetails.setSkuId(createOrderDto.getSkuId());
        orderDetails.setSpecModel(pdSku.getSkuName());
        orderDetails.setRetailPrice(pdSku.getMarketPrice());
        orderDetails.setStockPrice(pdSku.getCostPrice());
        if (pdProductInfo.getBrandId() != null && pdProductInfo.getBrandId() != 0L){
            SysCodeInfo codeInfo = codeInfoMapper.selectByPrimaryKey(pdProductInfo.getBrandId());
            orderDetails.setBrand(codeInfo.getCodeName());
        }
        if (pdProductInfo.getSupplierId() != null && pdProductInfo.getSupplierId() != 0L){
            CmSupplierDictionaries supplierDictionaries = supplierDictionariesMapper.selectByPrimaryKey(pdProductInfo.getSupplierId());
            orderDetails.setSupplier(supplierDictionaries.getSupplierName());
        }
        orderDetails.setTakeGoodsName(createOrderDto.getTakeGoodsName());
        orderDetails.setTakeGoodsPhoneno(createOrderDto.getTakeGoodsPhoneno());
        orderDetails.setTakeGoodsAddress(createOrderDto.getTakeGoodsAddress());
        orderDetails.setCreateTime(new Date());
        orderDetails.setPrice(pdProductInfo.getPrice());
        orderDetails.setChannelId(pdProductInfo.getChannelId());
        int insertOrderDetail = omOrderDetailsMapper.insert(orderDetails);

        //保存订单状态记录
        OmOrderStatusRec orderStatusRec = new OmOrderStatusRec();
        orderStatusRec.setOrderNumber(orderNum);
        orderStatusRec.setStatus(OrderStatusEnum.ORDER_UNPAIED.getStatus());
        orderStatusRec.setCreateTime(new Date());
        int insertStatusRec = omOrderStatusRecMapper.insert(orderStatusRec);

        if (insertOrder > 0 && insertOrderDetail > 0 && insertStatusRec > 0) {
            return orderNum;
        }
        throw new ServiceException(ProductErrorCode.SAVE_FAIL, "创建订单失败");
    }

    @Override
    public Boolean saveTkTradeNo(String orderNumber, String TkTradeNo) {
        OmOrderDetails orderDetails = omOrderDetailsMapper.selectByOrderNumber(orderNumber);
        orderDetails.setDetailsOrderId(TkTradeNo);
        int i = omOrderDetailsMapper.updateByPrimaryKeySelective(orderDetails);
        return i > 0;
    }


    @Override
    public HashMap<String, Object> updatePayStatus(String TkTradeNo) {
        OmOrderDetails orderDetails = omOrderDetailsMapper.selectDetailsOrderId(TkTradeNo);
        if (orderDetails != null) {
            OmOrder omOrder = orderMapper.selectByPrimaryKey(orderDetails.getOrderId());
            if (omOrder != null && !omOrder.getPayStatus().equals(PayStatusEnum.PAID.getStatus())) {
                omOrder.setPayStatus(PayStatusEnum.PAID.getStatus());
                omOrder.setPayWay(PayWayEnum.TKPAY.name());
                omOrder.setStatus(OrderStatusEnum.EVALUATED.getStatus());
                omOrder.setPayTime(new Date());
                orderMapper.updateByPrimaryKeySelective(omOrder);

                HashMap<String, Object> result = new HashMap();
                result.put("accid", omOrder.getBuyAccount());
                result.put("orderNumber", omOrder.getOrderNumber());
                return result;
            }

        }
        return null;
    }

    @Override
    public void savePolicy(String TkTradeNo, String insureNum, String policyUrl) {
        OmOrderDetails orderDetails = omOrderDetailsMapper.selectDetailsOrderId(TkTradeNo);
        if (orderDetails != null) {
            orderDetails.setInsureNum(insureNum);
            orderDetails.setPolicyUrl(policyUrl);
            orderDetails.setModifyTime(new Date());
            omOrderDetailsMapper.updateByPrimaryKeySelective(orderDetails);
        }

    }


    /**
     * 生成交易单号
     *
     * @return
     */
    private String generatorOrderNum() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String dateTime = dateFormat.format(calendar.getTime());
        dateTime = dateTime.substring(2);
        String timestampPart = "" + (Math.random() * 10000) * (System.currentTimeMillis() / 10000);
        timestampPart = timestampPart.replace(".", "").replace("E", "");
        timestampPart = timestampPart.substring(0, 5);
        return dateTime + timestampPart;
    }


}
