package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.OrderDto;
import com.kingyon.chengxin.product.dto.query.OrderExportQuery;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.CreateOrderDto;
import com.kingyon.chengxin.product.dto.request.OrderRefund;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Date: 2018/10/23 0023 16:48
 */
public interface OmOrderService extends BaseService {
   /**
    * 创建订单
    * @param accid
    * @param productId
    * @param price
    * @param insureNum
    * @return
    */
   String createOrder(Long accid, Long productId, BigDecimal price,String insureNum,String remark);

   /**
    * 根据用户ID查询未知订单
    * @param accid
    * @param orderNum
    * @return
    */
   Map<String, Object> queryOrder(Long accid, String orderNum);

   /**
    * 检查订单是否是已支付状态
    * @param accid
    * @param orderNum
    * @return
    */
   Map<String, Object> checkOrder(Long accid, String orderNum);

   /**
     *  后台订单列表
     * @param orderQuery
     * @return
     */
    PageDto orderList(OrderQuery orderQuery);

    /**
     *  后台订单列表
     * @param orderQuery
     * @return
     */
    PageDto orderListExport(OrderExportQuery orderQuery);

   /**
    * 后台系统查看订单详情
    * @param orderNumber
    * @return
    */
    Map<String,Object> orderDetail(String orderNumber) throws Exception;

    /**
     * 订单详情备注
     * @param orderNumber
     * @param remark
     * @return
     */
    Boolean orderDetailRemark(String orderNumber, String remark);

    /**
     * 线下确认退款
     * @param orderRefund
     * @return
     */
    Boolean offlineRefund(OrderRefund orderRefund);

    /**
     * 商城创建订单
     * @param createOrderDto
     * @return
     */
    String createMallOrder(CreateOrderDto createOrderDto);

    /**
     * 添加泰康支付流水号
     */
   Boolean saveTkTradeNo(String orderNumber,String TkTradeNo);

    /**
     * 通过泰康的支付流水号,修改订单状态
     */
    HashMap<String,Object> updatePayStatus(String TkTradeNo);

    /**
     * 通过泰康的支付流水号,增加保单号和保单下载URL
     */
    void savePolicy(String TkTradeNo,String insureNum,String policyUrl);
}
