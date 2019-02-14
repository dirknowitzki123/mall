package com.kingyon.chengxin.product.dto;

import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Auther: Aspen
 * @Created: 2019/1/4 0004.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkuDto extends BaseDto {

    private Long id;
    /**
     * 商品ID
     *
     * @time 2019-01-04 17:23:53
     */
    private Long productId;

    /**
     * SKU编码
     *
     * @time 2019-01-04 17:23:53
     */
    private String skuCode;

    @NotBlank(message = "skuName不能为空")
    private String skuName;

    /**
     * 市场价
     *
     * @time 2019-01-04 17:23:53
     */
    private BigDecimal marketPrice;

    /**
     * 成本价
     *
     * @time 2019-01-04 17:23:53
     */
    private BigDecimal costPrice;

    /**
     * 销量
     *
     * @time 2019-01-04 17:23:53
     */
    private Integer saleCount;

    /**
     * 可用库存
     *
     * @time 2019-01-04 17:23:53
     */
    private Integer stock;

    /**
     * 排序
     *
     * @time 2019-01-04 17:23:53
     */
    private Integer sort;

    /**
     * 预览图
     *
     * @time 2019-01-04 17:23:53
     */
    private String skuMainPic;

    /**
     * 是否启用(0 禁用 1 启用)
     *
     * @time 2019-01-04 17:23:53
     */
    private Byte status;

}
