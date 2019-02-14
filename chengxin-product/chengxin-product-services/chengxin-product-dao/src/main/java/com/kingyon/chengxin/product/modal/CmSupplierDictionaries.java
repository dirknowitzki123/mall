package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.util.Date;

public class CmSupplierDictionaries extends BaseEntity {
    /**
     * 供应商名称
     * @time 2018-10-16 10:55:37
     */
    private String supplierName;

    /**
     * 说明
     * @time 2018-10-16 10:55:37
     */
    private String description;

    /**
     * 备注
     * @time 2018-10-16 10:55:37
     */
    private String remark;

    /**
     * 操作时间
     * @time 2018-10-16 10:55:37
     */
    private Date operatorTime;

    /**
     * 关联后台账户表
     * @time 2018-10-16 10:55:37
     */
    private Long operator;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}