package com.kingyon.chengxin.product.dto.query;

import com.kingyon.chengxin.framework.dto.BaseQuery;
import lombok.Data;

/**
 * @Auther: Aspen
 * @Created: 2018/11/16 0016.
 */
@Data
public class UserAccountQuery extends BaseQuery {
    private String  beginDate;
    private String  endDate;
}
