package com.kingyon.chengxin.product.dto.request;

import com.kingyon.chengxin.framework.annotation.FieldMapped;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OperateRefund extends BaseDto {
	/**
	 * 退款订单编号
	 * @time 2019-1-7
	 */
	@NotNull
	private String orderNumber;

	/**
	 * 退款金额
	 * @time 2019-1-7
	 */
	@NotNull
	private BigDecimal refundMoney;

	/**
	 * 退款操作(同意或拒绝)
	 * @time 2019-1-7
	 */
	@NotNull
	//@FieldMapped(names = "refundStatus")
	private String refundStatus;

	/**
	 * 退款备注
	 * @time 2019-1-7
	 */
	private String refundReason;

	/**
	 * 拒绝原因
	 * @time 2019-1-10
	 */
	private String rejectReason;
}
