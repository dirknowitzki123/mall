package com.kingyon.chengxin.product.dto.request;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReceiveAddressDto extends BaseDto {

	/**
	 * 用户唯一账号
	 * @time 2019-01-07 17:47:19
	 */
	@NotNull
	private Long accountId;

	/**
	 * 联系电话
	 * @time 2019-01-07 17:47:19
	 */
	@NotNull
	private String phone;

	/**
	 * 收货人姓名
	 * @time 2019-01-07 17:47:19
	 */
	@NotNull
	private String consignee;

	/**
	 * 详情地址
	 * @time 2019-01-07 17:47:19
	 */
	@NotNull
	private String detailAddress;

	/**
	 * 是否默认地址
	 */
	private Integer defaultAddress;
}
