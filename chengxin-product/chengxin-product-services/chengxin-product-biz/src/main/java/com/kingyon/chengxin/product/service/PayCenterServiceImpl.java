package com.kingyon.chengxin.product.service;

import com.kingyon.chengxin.framework.annotation.DubboService;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.product.OrderErrorCode;
import com.kingyon.chengxin.product.api.PayCenterService;
import com.kingyon.chengxin.product.enums.OrderStatusEnum;
import com.kingyon.chengxin.product.enums.PayStatusEnum;
import com.kingyon.chengxin.product.mapper.OmOrderMapper;
import com.kingyon.chengxin.product.mapper.OmOrderStatusRecMapper;
import com.kingyon.chengxin.product.modal.OmOrder;
import com.kingyon.chengxin.product.modal.OmOrderDetails;
import com.kingyon.chengxin.product.modal.OmOrderStatusRec;
import com.kingyon.chengxin.product.modal.UmUserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

@DubboService
@Slf4j
public class PayCenterServiceImpl implements PayCenterService {

	@Autowired
	OmOrderMapper orderMapper;

	@Autowired
	OmOrderStatusRecMapper orderStatusRecMapper;

	@Override
	public Map<String, String> confirmOrderStatus(String orderNumber, Integer totalFee) {
		Map<String, String> retMap = new HashMap<>();
		OmOrder omOrder = orderMapper.selectOrderByOrderNo(orderNumber);
		//如果订单未支付
		if (omOrder != null && omOrder.getPayStatus().equals(0)) {
			log.info("支付的订单金额......................................." + totalFee);
			if (omOrder.getAmount().multiply(new BigDecimal(100)).intValue() == totalFee) {
				omOrder.setPayStatus(PayStatusEnum.PAID.getStatus());
				omOrder.setStatus(OrderStatusEnum.UNDELIVER.getStatus());
				omOrder.setPayWay("WX");
				omOrder.setPayTime(new Date());
				orderMapper.updateByPrimaryKeySelective(omOrder);
				log.info("订单状态修改成功...................................." + totalFee);

				//保存订单状态记录
				OmOrderStatusRec orderStatusRec = new OmOrderStatusRec();
				orderStatusRec.setOrderNumber(orderNumber);
				orderStatusRec.setStatus(OrderStatusEnum.UNDELIVER.getStatus());
				orderStatusRec.setCreateTime(new Date());
				orderStatusRecMapper.insert(orderStatusRec);

				retMap.put("return_code", "SUCCESS");
				retMap.put("return_msg", "OK");
				return retMap;
			} else {
				retMap.put("return_code", "FAIL");
				retMap.put("return_msg", "订单金额有误");
				return retMap;
			}
		} else {
			retMap.put("return_code", "FAIL");
			retMap.put("return_msg", "订单不存在或状态为已支付");
			return retMap;
		}
	}
}
