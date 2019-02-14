package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class UmUserReceivingAddress extends BaseEntity {
    /**
     * 用户唯一账号
     * @time 2019-01-07 17:47:19
     */
    private Long accountId;

    /**
     * 关联用户账号
     * @time 2019-01-07 17:47:19
     */
    private Long userArchivesId;

    /**
     * 详情地址
     * @time 2019-01-07 17:47:19
     */
    private String detailAddress;

    /**
     * 调用地图API产生的数据
     * @time 2019-01-07 17:47:19
     */
    private String regionId;

    /**
     * 联系电话
     * @time 2019-01-07 17:47:19
     */
    private String phone;

    /**
     * 收货人姓名
     * @time 2019-01-07 17:47:19
     */
    private String consignee;

    /**
     * 删除标识
     * @time 2019-01-07 17:47:19
     */
    private Byte deleted;

    /**
     * 备注
     * @time 2019-01-07 17:47:19
     */
    private String remark;

    /**
     * 关联后台账户表
     * @time 2019-01-07 17:47:19
     */
    private Long operator;

    /**
     * 默认地址
     * @time 2019-01-07 17:47:19
     */
    private Integer defaultAddress;

    /**
     * 地区详情
     * @time 2019-01-07 17:47:19
     */
    private String detailRegion;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserArchivesId() {
        return userArchivesId;
    }

    public void setUserArchivesId(Long userArchivesId) {
        this.userArchivesId = userArchivesId;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
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

    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getDetailRegion() {
        return detailRegion;
    }

    public void setDetailRegion(String detailRegion) {
        this.detailRegion = detailRegion == null ? null : detailRegion.trim();
    }
}