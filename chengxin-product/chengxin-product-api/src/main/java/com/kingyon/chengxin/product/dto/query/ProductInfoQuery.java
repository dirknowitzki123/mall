package com.kingyon.chengxin.product.dto.query;

import com.kingyon.chengxin.framework.dto.BaseDto;
import com.kingyon.chengxin.framework.dto.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoQuery extends BaseQuery {


    /**
     * 产品名称
     * @time 2018-10-15 11:39:25
     */
    private String productName;
    /**
     * 0未上架1上架
     * @time 2018-10-15 11:39:25
     */
    private Byte putaway;

    /**
     * 产品类型  10001-保险,10002-套餐,10003-体检 ,1004 其他.....
     * @time 2018-10-15 11:39:25
     */
    private Integer proType;
}
