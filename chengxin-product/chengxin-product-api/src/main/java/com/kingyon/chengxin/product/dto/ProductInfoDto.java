package com.kingyon.chengxin.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingyon.chengxin.framework.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Aspen
 * @Date: 2018/10/15 0015 11:47
 */
@Data
public class ProductInfoDto extends BaseDto {

    private  Long id;
    /**
     * 产品名称
     * @time 2018-10-15 11:39:25
     */
    @NotNull(message = "产品名称不能为空")
    private String productName;

    /**
     * 产品副标题
     * @time 2018-10-15 11:39:25
     */
    private String subtitle;

    /**
     * 主图地址
     * @time 2018-10-15 11:39:25
     */
    private String mainImage;

    /**
     * 产品单价
     * @time 2018-10-15 11:39:25
     */
//    @NotNull(message = "产品单价不能为空")
    private BigDecimal price;

    /**
     * 0未上架1上架
     * @time 2018-10-15 11:39:25
     */
    private Byte putaway;


    /**
     * 产品类型  10001-保险,10002-套餐,10003-体检 ,10004 其他 ,10005 实体.....
     * @time 2018-10-15 11:39:25
     */
    private Integer proType;

    /**
     * 关联供应商表id
     * @time 2018-10-15 11:39:25
     */
    private Long supplierId;

    /**
     * 销售量
     * @time 2018-10-15 11:39:25
     */
    private Long salesAmount;

    /**
     * 前端访问地址
     * @time 2018-10-15 11:39:25
     */
    private String visitUrl;

    /**
     * 标签
     * @time 2018-10-15 11:39:25
     */
    private String tag;

    /**
     * 产品编号
     * @time 2018-10-15 11:39:25
     */
    private String productCode;

    /**
     * 产品详情
     * @time 2018-10-15 11:39:25
     */
    private String productDetail;

    /**
     * 渠道商ID
     * @time 2018-10-16 13:15:30
     */
    private Long channelId;

    /**
     * 品牌ID
     * @time 2019-01-04 14:19:31
     */
    private Long brandId;

    /**
     * 商品的sku属性
     */
    private List<SkuDto> skus = new ArrayList<>();

}
