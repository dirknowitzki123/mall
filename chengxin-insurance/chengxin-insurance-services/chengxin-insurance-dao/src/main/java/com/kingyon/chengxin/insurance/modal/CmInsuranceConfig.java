package com.kingyon.chengxin.insurance.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;

public class CmInsuranceConfig extends BaseEntity {
    /**
     * 名称
     * @time 2018-09-19 09:33:16
     */
    private String insuranceName;

    /**
     * 副标题
     * @time 2018-09-19 09:33:16
     */
    private String insuranceTitle;

    /**
     * 开关
     * @time 2018-09-19 09:33:16
     */
    private Boolean onOff;

    /**
     * 主图地址
     * @time 2018-09-19 09:33:16
     */
    private String mainImage;

    /**
     * 价格
     * @time 2018-09-19 09:33:16
     */
    private BigDecimal price;

    /**
     * 关联供应商表
     * @time 2018-09-19 09:33:16
     */
    private Long supplierId;

    /**
     * 0未上架1上架
     * @time 2018-09-19 09:33:16
     */
    private Boolean putaway;

    /**
     * 链接
     * @time 2018-09-19 09:33:16
     */
    private String address;

    /**
     * 用于：保险
     * @time 2018-09-19 09:33:16
     */
    private String insureAddress;

    /**
     * 保额
     * @time 2018-09-19 09:33:16
     */
    private BigDecimal coverage;

    /**
     * 免赔额
     * @time 2018-09-19 09:33:16
     */
    private BigDecimal deductibles;

    /**
     * 天
     * @time 2018-09-19 09:33:16
     */
    private Integer indate;

    /**
     * 天
     * @time 2018-09-19 09:33:16
     */
    private Integer insuranceIndate;

    /**
     * 最多添加三个用特殊符合分割
     * @time 2018-09-19 09:33:16
     */
    private String tag;

    /**
     * 关联后台账户表
     * @time 2018-09-19 09:33:16
     */
    private Long operator;



    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName == null ? null : insuranceName.trim();
    }

    public String getInsuranceTitle() {
        return insuranceTitle;
    }

    public void setInsuranceTitle(String insuranceTitle) {
        this.insuranceTitle = insuranceTitle == null ? null : insuranceTitle.trim();
    }

    public Boolean getOnOff() {
        return onOff;
    }

    public void setOnOff(Boolean onOff) {
        this.onOff = onOff;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Boolean getPutaway() {
        return putaway;
    }

    public void setPutaway(Boolean putaway) {
        this.putaway = putaway;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getInsureAddress() {
        return insureAddress;
    }

    public void setInsureAddress(String insureAddress) {
        this.insureAddress = insureAddress == null ? null : insureAddress.trim();
    }

    public BigDecimal getCoverage() {
        return coverage;
    }

    public void setCoverage(BigDecimal coverage) {
        this.coverage = coverage;
    }

    public BigDecimal getDeductibles() {
        return deductibles;
    }

    public void setDeductibles(BigDecimal deductibles) {
        this.deductibles = deductibles;
    }

    public Integer getIndate() {
        return indate;
    }

    public void setIndate(Integer indate) {
        this.indate = indate;
    }

    public Integer getInsuranceIndate() {
        return insuranceIndate;
    }

    public void setInsuranceIndate(Integer insuranceIndate) {
        this.insuranceIndate = insuranceIndate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

}