package com.kingyon.chengxin.product.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingyon.chengxin.framework.PageDto;
import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.product.OrderErrorCode;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.OmOrderDetailsService;
import com.kingyon.chengxin.product.dto.query.OrderQuery;
import com.kingyon.chengxin.product.dto.request.OperateRefund;
import com.kingyon.chengxin.product.dto.request.ReceiveAddressDto;
import com.kingyon.chengxin.product.enums.OrderStatusEnum;
import com.kingyon.chengxin.product.enums.PayStatusEnum;
import com.kingyon.chengxin.product.mapper.*;
import com.kingyon.chengxin.product.modal.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Slf4j
public class OmOrderDetailsServiceImpl implements OmOrderDetailsService {

	@Autowired
	OmOrderMapper orderMapper;

	@Autowired
	OmOrderDetailsMapper orderDetailsMapper;

	@Autowired
	OmOrderRefundMapper orderRefundMapper;

	@Autowired
	OmOrderStatusRecMapper orderStatusRecMapper;

	@Autowired
	UmUserReceivingAddressMapper receivingAddressMapper;

	@Autowired
	UmUserAccountMapper userAccountMapper;

	@Autowired
	PdSkuMapper skuMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean agreeRefundOrNot(OperateRefund operateRefund) {
		Map<String, Object> order = orderMapper.orderDetail(operateRefund.getOrderNumber());
		if (order == null) {
			throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
		}
		Object payStatus = order.get("payStatus");
		if (!PayStatusEnum.PAID.getStatus().equals(payStatus)) {
			throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER, "订单未支付不能发起退款");
		}

		//保存退款信息
		OmOrderDetails orderDetails = orderDetailsMapper.selectByOrderNumber(operateRefund.getOrderNumber());
		OmOrderRefund omOrderRefund = operateRefund.copyTo(OmOrderRefund.class);
		omOrderRefund.setOrderDetailId(orderDetails.getId().intValue());
		omOrderRefund.setRefundTime(new Date());
		omOrderRefund.setDeleted((byte) 0);
		orderRefundMapper.insert(omOrderRefund);

		//保存订单状态记录
		OmOrderStatusRec orderStatusRec = new OmOrderStatusRec();
		orderStatusRec.setOrderNumber(operateRefund.getOrderNumber());
		orderStatusRec.setStatus("0".equals(operateRefund.getRefundStatus()) ? OrderStatusEnum.UNDELIVER.getStatus() :
				OrderStatusEnum.REFUNDED.getStatus());
		orderStatusRec.setCreateTime(new Date());
		orderStatusRecMapper.insert(orderStatusRec);

		//修改订单状态
		OmOrder omOrder = new OmOrder();
		Long orderId = (Long) order.get("id");
		omOrder.setId(orderId);
		if ("0".equals(operateRefund.getRefundStatus())){
			//拒绝退款
			omOrder.setStatus(OrderStatusEnum.UNDELIVER.getStatus());
		} else if("1".equals(operateRefund.getRefundStatus())){
			//同意退款
			omOrder.setStatus(OrderStatusEnum.REFUNDED.getStatus());
			omOrder.setPayStatus(PayStatusEnum.REFUNDED.getStatus());
		} else {
			throw new ServiceException(OrderErrorCode.ORDER_REFUND_STATUS_INPUT_ERROR, "退款状态输入错误");
		}
		omOrder.setModifyTime(new Date());
		int i = orderMapper.updateByPrimaryKeySelective(omOrder);
		return i > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean refundRejectReason(String orderNumber, String rejectReason) {
		//查询订单是否存在
		Map<String, Object> order = orderMapper.orderDetail(orderNumber);
		if (order == null) {
			throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
		}

		//保存拒绝退款理由
		OmOrderDetails orderDetails = orderDetailsMapper.selectByOrderNumber(orderNumber);
		OmOrderRefund omOrderRefund = orderRefundMapper.selectByOrderDetailId(orderDetails.getId().intValue());
		omOrderRefund.setRejectReason(rejectReason);
		int result = orderRefundMapper.updateByPrimaryKeySelective(omOrderRefund);
		return result > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveExpressInfo(String orderNumber, String expressCompany, String expressNo) {
		Map<String, Object> order = orderMapper.orderDetail(orderNumber);
		if (order == null) {
			throw new ServiceException(ProductErrorCode.NOT_FOUND_ORDER);
		}

		//保存订单状态记录
		OmOrderStatusRec orderStatusRec = new OmOrderStatusRec();
		orderStatusRec.setOrderNumber(orderNumber);
		orderStatusRec.setStatus(OrderStatusEnum.UNTAKE_GOODS.getStatus());
		orderStatusRec.setCreateTime(new Date());
		orderStatusRecMapper.insert(orderStatusRec);

		//保存快递信息
		OmOrderDetails orderDetails = orderDetailsMapper.selectByOrderNumber(orderNumber);
		orderDetails.setExpressCompany(expressCompany);
		orderDetails.setExpressNo(expressNo);
		orderDetails.setModifyTime(new Date());
		int result = orderDetailsMapper.updateByPrimaryKeySelective(orderDetails);

		//修改订单状态
		OmOrder omOrder = orderMapper.selectOrderByOrderNo(orderNumber);
		omOrder.setStatus(OrderStatusEnum.UNTAKE_GOODS.getStatus());
		omOrder.setModifyTime(new Date());
		int update = orderMapper.updateByPrimaryKeySelective(omOrder);

		//扣库存
		PdSku pdSku = skuMapper.selectByPrimaryKey(orderDetails.getSkuId());
		if (pdSku.getStock() <= 0){
			throw new ServiceException(ProductErrorCode.NOT_ENOUGH_SKU);
		}
		skuMapper.updateSkuStock(orderDetails.getSkuId());

		return update > 0;
	}

	@Override
	public PageDto receiveAddressList(Long accid) {
		//PageHelper.startPage(orderQuery.getPageIndex(), orderQuery.getPageSize());
		List<Map<String, Object>> orderList = receivingAddressMapper.selectAddressList(accid);
		//PageInfo pageInfo = new PageInfo(orderList);
		PageDto pageDto = new PageDto();
		//pageDto.setDataCount(Integer.parseInt(pageInfo.getTotal() + ""));
		//pageDto.setPageIndex(pageInfo.getPageNum());
		//pageDto.setPageSize(pageInfo.getPageSize());
		pageDto.setData(orderList);
		return pageDto;
	}

	@Override
	public PageDto defaultReceiveAddress(Long accid) {
		Map<String, Object> defaultAddressMap = receivingAddressMapper.defaultReceiveAddress(accid, 1);
		PageDto pageDto = new PageDto();
		pageDto.setOtherField(defaultAddressMap);
		return pageDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addReceiveAddress(ReceiveAddressDto addressDto) {
		//检查用户是否存在
		UmUserAccount userAccount = userAccountMapper.selectByPrimaryKey(addressDto.getAccountId());
		if (userAccount == null){
			throw new ServiceException(OrderErrorCode.USER_ACCOUNT_INFO_NOT_EXIST);
		}

		int result = 0;
		UmUserReceivingAddress receivingAddress = receivingAddressMapper.selectReceiveAddress(addressDto.getAccountId(),
				addressDto.getPhone(), addressDto.getConsignee(), addressDto.getDetailAddress());
		if (receivingAddress == null) {
			//新增地址信息
			receivingAddress = addressDto.copyTo(UmUserReceivingAddress.class);
			receivingAddress.setAccountId(addressDto.getAccountId());
			if (addressDto.getDefaultAddress() == null || addressDto.getDefaultAddress() == 0) {
				receivingAddress.setDefaultAddress(0);
			}
			receivingAddress.setCreateTime(new Date());
			result = receivingAddressMapper.insert(receivingAddress);
		} else {
			//更新地址信息
			receivingAddress.setDefaultAddress(addressDto.getDefaultAddress());
			receivingAddress.setModifyTime(new Date());
			result = receivingAddressMapper.updateByPrimaryKeySelective(receivingAddress);
		}

		//把其他的收货地址设置为非默认
		if (addressDto.getDefaultAddress() == 1) {
			List<UmUserReceivingAddress> receAddressList = receivingAddressMapper.selectUndefaultAddress(receivingAddress.getId(), addressDto.getAccountId());
			for (UmUserReceivingAddress receAddress : receAddressList) {
				receAddress.setDefaultAddress(0);
				receAddress.setModifyTime(new Date());
				receivingAddressMapper.updateByPrimaryKeySelective(receAddress);
			}
		}

		return result > 0;
	}

	@Override
	public Map<String, Object> orderDetail(String orderNumber) {
		Map<String, Object> retMap = orderMapper.selectOrderDetail(orderNumber);
		retMap.put("orderStatusList", orderStatusRecMapper.selectList(orderNumber));
		return retMap;
	}

	@Override
	public boolean confirmReceipt(Long accid, String orderNumber) {
		OmOrder omOrder = orderMapper.selectOrderByOrderNoAndAccid(accid, orderNumber);
		if (omOrder == null){
			throw new ServiceException(OrderErrorCode.ORDER_INFO_NOT_EXIST);
		}
		omOrder.setStatus(OrderStatusEnum.TAKEN_GOODS.getStatus());
		omOrder.setModifyTime(new Date());
		int result = orderMapper.updateByPrimaryKeySelective(omOrder);

		//保存订单状态记录
		OmOrderStatusRec orderStatusRec = new OmOrderStatusRec();
		orderStatusRec.setOrderNumber(orderNumber);
		orderStatusRec.setStatus(OrderStatusEnum.TAKEN_GOODS.getStatus());
		orderStatusRec.setCreateTime(new Date());
		orderStatusRecMapper.insert(orderStatusRec);

		return result > 0;
	}
}
