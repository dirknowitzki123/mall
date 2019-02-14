package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;

public class OmOrderDetails extends BaseEntity {
    /**
     * 关联主订单表中的订单id
     * @time 2019-01-08 10:14:54
     */
    private Long orderId;

    /**
     * 子订单号
     * @time 2019-01-08 10:14:54
     */
    private String detailsOrderId;

    /**
     * 数量
     * @time 2019-01-08 10:14:54
     */
    private Integer buyNum;

    /**
     * 价格
     * @time 2019-01-08 10:14:54
     */
    private BigDecimal price;

    /**
     * (10001:保险产品,10002:套餐, 10003:医疗服务,10004:实物商品)
     * @time 2019-01-08 10:14:54
     */
    private Integer projectType;

    /**
     * 订单项目详情ID(此字段已废弃)
     * @time 2019-01-08 10:14:54
     */
    private Long orderProjectId;

    /**
     * 产品名称
     * @time 2019-01-08 10:14:54
     */
    private String productName;

    /**
     * 产品副标题
     * @time 2019-01-08 10:14:54
     */
    private String subtitle;

    /**
     * 主图地址
     * @time 2019-01-08 10:14:54
     */
    private String mainImage;

    /**
     * 投保单号
     * @time 2019-01-08 10:14:54
     */
    private String insureNum;

    /**
     * 保单下载地址
     * @time 2019-01-08 10:14:54
     */
    private String policyUrl;

    /**
     * 渠道ID(3340001:齐欣慧择,3340002:泰康)
     * @time 2019-01-08 10:14:54
     */
    private Long channelId;

    /**
     * 备注
     * @time 2019-01-08 10:14:54
     */
    private String remark;

    /**
     * 关联后台账户表
     * @time 2019-01-08 10:14:54
     */
    private Long operator;

    /**
     * 产品ID(pd_product_info 表)
     * @time 2019-01-08 10:14:54
     */
    private Long comboProjectId;

    /**
     * SKU-ID(pd_sku表)
     * @time 2019-01-08 10:14:54
     */
    private Long skuId;

    /**
     * 规格型号
     * @time 2019-01-08 10:14:54
     */
    private String specModel;

    /**
     * 零售价
     * @time 2019-01-08 10:14:54
     */
    private BigDecimal retailPrice;

    /**
     * 进货价
     * @time 2019-01-08 10:14:54
     */
    private BigDecimal stockPrice;

    /**
     * 品牌
     * @time 2019-01-08 10:14:54
     */
    private String brand;

    /**
     * 供应商
     * @time 2019-01-08 10:14:54
     */
    private String supplier;

    /**
     * 快递公司
     * @time 2019-01-08 10:14:54
     */
    private String expressCompany;

    /**
     * 运单号
     * @time 2019-01-08 10:14:54
     */
    private String expressNo;

    /**
     * 收货姓名
     * @time 2019-01-08 10:14:54
     */
    private String takeGoodsName;

    /**
     * 收货手机号
     * @time 2019-01-08 10:14:54
     */
    private String takeGoodsPhoneno;

    /**
     * 收货地址
     * @time 2019-01-08 10:14:54
     */
    private String takeGoodsAddress;

    /**
     * 0否1是
     * @time 2019-01-08 10:14:54
     */
    private Byte deleted;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDetailsOrderId() {
        return detailsOrderId;
    }

    public void setDetailsOrderId(String detailsOrderId) {
        this.detailsOrderId = detailsOrderId == null ? null : detailsOrderId.trim();
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Long getOrderProjectId() {
        return orderProjectId;
    }

    public void setOrderProjectId(Long orderProjectId) {
        this.orderProjectId = orderProjectId;
    }

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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getInsureNum() {
        return insureNum;
    }

    public void setInsureNum(String insureNum) {
        this.insureNum = insureNum == null ? null : insureNum.trim();
    }

    public String getPolicyUrl() {
        return policyUrl;
    }

    public void setPolicyUrl(String policyUrl) {
        this.policyUrl = policyUrl == null ? null : policyUrl.trim();
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getComboProjectId() {
        return comboProjectId;
    }

    public void setComboProjectId(Long comboProjectId) {
        this.comboProjectId = comboProjectId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSpecModel() {
        return specModel;
    }

    public void setSpecModel(String specModel) {
        this.specModel = specModel == null ? null : specModel.trim();
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public String getTakeGoodsName() {
        return takeGoodsName;
    }

    public void setTakeGoodsName(String takeGoodsName) {
        this.takeGoodsName = takeGoodsName == null ? null : takeGoodsName.trim();
    }

    public String getTakeGoodsPhoneno() {
        return takeGoodsPhoneno;
    }

    public void setTakeGoodsPhoneno(String takeGoodsPhoneno) {
        this.takeGoodsPhoneno = takeGoodsPhoneno == null ? null : takeGoodsPhoneno.trim();
    }

    public String getTakeGoodsAddress() {
        return takeGoodsAddress;
    }

    public void setTakeGoodsAddress(String takeGoodsAddress) {
        this.takeGoodsAddress = takeGoodsAddress == null ? null : takeGoodsAddress.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}