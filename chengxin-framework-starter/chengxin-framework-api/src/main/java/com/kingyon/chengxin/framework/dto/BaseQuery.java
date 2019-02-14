package com.kingyon.chengxin.framework.dto;

import lombok.Data;

/**
 * @Auther: Aspen
 * @Created: 2018/11/14 0014.
 */
@Data
public class BaseQuery  extends BaseDto{

    // 每页返回行数
    private int pageSize = 10;
    // 当前页码
    private int pageIndex = 1;

    private String sort ="ASC";

    private String keyword;

}
