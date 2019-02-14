package com.kingyon.chengxin.insurance.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.util.Date;

public class QxPolicyInfo extends BaseEntity {

    private String insureNum;

    private String productName;

    private String planName;

    private String applicant;

    private String insurant;

    private String startDate;

    private String endDate;

    private String policyNum;


    public String getInsureNum() {
        return insureNum;
    }

    public void setInsureNum(String insureNum) {
        this.insureNum = insureNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getInsurant() {
        return insurant;
    }

    public void setInsurant(String insurant) {
        this.insurant = insurant == null ? null : insurant.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getPolicyNum() {
        return policyNum;
    }

    public void setPolicyNum(String policyNum) {
        this.policyNum = policyNum == null ? null : policyNum.trim();
    }
}