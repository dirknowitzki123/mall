package com.kingyon.chengxin.product.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: duxin
 * @Created: 2019/1/17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderExportQuery implements Serializable {
	protected static long serialVersionUID = 1L;

	private Integer productType;
	private String  beginDate;
	private String  endDate;
	private Long accid;
	private Integer status;
	private String keyword;
}
