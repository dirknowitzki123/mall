package com.kingyon.chengxin.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingyon.chengxin.framework.dto.BaseDto;
import com.kingyon.chengxin.product.dto.ProductInfoDto;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Aspen
 * @Date: 2018/10/16 0016 15:52
 */
@Data
public class ProductListDto extends ProductInfoDto {

    private String supplierName;

    private String channelName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
