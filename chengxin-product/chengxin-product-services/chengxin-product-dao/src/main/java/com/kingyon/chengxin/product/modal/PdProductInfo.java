package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;

public class PdProductInfo extends BaseEntity {
    /**
     * 产品名称
     * @time 2019-01-04 14:19:31
     */
    private String productName;

    /**
     * 产品副标题
     * @time 2019-01-04 14:19:31
     */
    private String subtitle;

    /**
     * 产品单价
     * @time 2019-01-04 14:19:31
     */
    private BigDecimal price;

    /**
     * 0未上架1上架
     * @time 2019-01-04 14:19:31
     */
    private Byte putaway;

    /**
     * 开关 0关.1开
     * @time 2019-01-04 14:19:31
     */
    private Byte onOff;

    /**
     * 产品类型  10001-保险,10002-套餐,10003-体检 ,1004 其他.....
     * @time 2019-01-04 14:19:31
     */
    private Integer proType;

    /**
     * 产品明细ID
     * @time 2019-01-04 14:19:31
     */
    private Long detailId;

    /**
     * 关联供应商表id
     * @time 2019-01-04 14:19:31
     */
    private Long supplierId;

    /**
     * 销售量
     * @time 2019-01-04 14:19:31
     */
    private Long salesAmount;

    /**
     * 前端访问地址
     * @time 2019-01-04 14:19:31
     */
    private String visitUrl;

    /**
     * 备注
     * @time 2019-01-04 14:19:31
     */
    private String remarks;

    /**
     * 渠道商ID
     * @time 2019-01-04 14:19:31
     */
    private Long channelId;

    /**
     * 主图地址
     * @time 2019-01-04 14:19:31
     */
    private String mainImage;

    /**
     * 标签
     * @time 2019-01-04 14:19:31
     */
    private String tag;

    /**
     * 产品编号
     * @time 2019-01-04 14:19:31
     */
    private String productCode;

    private Byte deleted;

    /**
     * 品牌ID
     * @time 2019-01-04 14:19:31
     */
    private Long brandId;

    /**
     * 产品详情
     * @time 2019-01-04 14:19:31
     */
    private String productDetail;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getPutaway() {
        return putaway;
    }

    public void setPutaway(Byte putaway) {
        this.putaway = putaway;
    }

    public Byte getOnOff() {
        return onOff;
    }

    public void setOnOff(Byte onOff) {
        this.onOff = onOff;
    }

    public Integer getProType() {
        return proType;
    }

    public void setProType(Integer proType) {
        this.proType = proType;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Long salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl == null ? null : visitUrl.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail == null ? null : productDetail.trim();
    }
}