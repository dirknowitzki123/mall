package com.kingyon.chengxin.product.api;

import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.api.BaseService;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.OperateRefund;
import com.kingyon.chengxin.product.dto.request.ReceiveAddressDto;

import java.util.List;
import java.util.Map;

public interface OmOrderDetailsService extends BaseService {

	/**
	 * 同意或者拒绝退款
	 * @param operateRefund
	 * @return
	 */
	boolean agreeRefundOrNot(OperateRefund operateRefund);

	/**
	 * 保存拒绝理由
	 * @param orderNumber
	 * @param rejectReason
	 * @return
	 */
	boolean refundRejectReason(String orderNumber, String rejectReason);

	/**
	 * 保存快递信息
	 * @param expressCompany
	 * @param expressNo
	 * @return
	 */
	boolean saveExpressInfo(String orderNumber, String expressCompany, String expressNo);

	/**
	 * 收货地址列表
	 * @param accid
	 * @return
	 */
	PageDto receiveAddressList(Long accid);

	/**
	 * 查询默认收货地址
	 * @param accid
	 * @return
	 */
	PageDto defaultReceiveAddress(Long accid);

	/**
	 * 新增收货地址
	 * @param addressDto
	 * @return
	 */
	boolean addReceiveAddress(ReceiveAddressDto addressDto);

	/**
	 * C端查看订单详情
	 * @param orderNumber
	 * @return
	 */
	Map<String,Object> orderDetail(String orderNumber);

	/**
	 * 确认收货
	 * @param accid
	 * @param orderNumber
	 * @return
	 */
	boolean confirmReceipt(Long accid, String orderNumber);

}
