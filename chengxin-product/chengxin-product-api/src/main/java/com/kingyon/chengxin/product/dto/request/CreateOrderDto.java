package com.kingyon.chengxin.product.dto.request;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateOrderDto extends BaseDto {
	/**用户ID*/
	@NotNull
	private Long accid;

	/**产品ID*/
	@NotNull
	private Long productId;

	/**SKU_ID*/
	@NotNull
	private Long skuId;

	/**规格型号*/
	private String specModel;

	/**价格*/
	@NotNull
	private BigDecimal price;

	/**数量*/
	@NotNull
	private Integer buyNum;

	/**收货姓名*/
	@NotNull
	private String takeGoodsName;

	/**收货手机号*/
	@NotNull
	private String takeGoodsPhoneno;

	/**收货地址*/
	@NotNull
	private String takeGoodsAddress;

	/**备注*/
	private String remark;

}

