package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class PdChannelProduct extends BaseEntity {
    /**
     * 渠道编号
     * @time 2018-11-22 11:42:09
     */
    private Long channelId;

    /**
     * 产品名称
     * @time 2018-11-22 11:42:09
     */
    private String prodName;

    /**
     * 渠道产品编码
     * @time 2018-11-22 11:42:09
     */
    private String productCode;

    /**
     * 保险公司
     * @time 2018-11-22 11:42:09
     */
    private String companyName;

    /**
     * 产品计划
     * @time 2018-11-22 11:42:09
     */
    private String planName;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }
}