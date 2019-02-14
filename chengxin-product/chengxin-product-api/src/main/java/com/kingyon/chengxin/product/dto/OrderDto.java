package com.kingyon.chengxin.product.dto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: Aspen
 * @Date: 2018/10/24 0024 09:48
 */
@Data
public class OrderDto extends BaseDto {

    private String orderNumber;
    private Long accid;
    private BigDecimal amount;
    private BigDecimal price;
    private String productName;
    private String subtitle;
    private String mainImage;
    private String insureNum;
    private Long productId;
    private Integer payStatus;
    private String remark;
    private String detailsOrderNum;
    private String channelId;

}
