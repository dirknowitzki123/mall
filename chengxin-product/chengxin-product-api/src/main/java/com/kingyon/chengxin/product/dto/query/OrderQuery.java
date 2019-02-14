package com.kingyon.chengxin.product.dto.query;

import com.kingyon.chengxin.framework.dto.BaseQuery;
import com.kingyon.chengxin.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: Aspen
 * @Created: 2018/11/14 0014.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderQuery extends BaseQuery {

    private Integer productType;
    private String  beginDate;
    private String  endDate;
    private Long accid;
    private Integer status;
    private String keyword;
}
