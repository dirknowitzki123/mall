package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;

public class PdSku extends BaseEntity {
    /**
     * 商品ID
     * @time 2019-01-04 17:23:53
     */
    private Long productId;

    /**
     * SKU编码
     * @time 2019-01-04 17:23:53
     */
    private String skuCode;

    private String skuName;

    /**
     * 市场价
     * @time 2019-01-04 17:23:53
     */
    private BigDecimal marketPrice;

    /**
     * 成本价
     * @time 2019-01-04 17:23:53
     */
    private BigDecimal costPrice;

    /**
     * 销量
     * @time 2019-01-04 17:23:53
     */
    private Integer saleCount;

    /**
     * 可用库存
     * @time 2019-01-04 17:23:53
     */
    private Integer stock;

    /**
     * 排序
     * @time 2019-01-04 17:23:53
     */
    private Integer sort;

    /**
     * 预览图
     * @time 2019-01-04 17:23:53
     */
    private String skuMainPic;

    /**
     * 是否启用(0 禁用 1 启用)
     * @time 2019-01-04 17:23:53
     */
    private Byte status;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSkuMainPic() {
        return skuMainPic;
    }

    public void setSkuMainPic(String skuMainPic) {
        this.skuMainPic = skuMainPic == null ? null : skuMainPic.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }


}