package com.kingyon.chengxin.insurance.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.util.Date;

public class QxOrderNotify extends BaseEntity {

    private Integer partnerId;

    private Long accid;

    private Integer notifyType;

    private String caseCode;

    private String insureNum;

    private String onlinePaymentId;

    private Long price;

    private String payTime;

    private Boolean state;

    private String failMsg;

    private String newInsureNum;

    private String cancelInsurants;

    private String cancelMsg;

    private String otherInfo;




    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Long getAccid() {
        return accid;
    }

    public void setAccid(Long accid) {
        this.accid = accid;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getInsureNum() {
        return insureNum;
    }

    public void setInsureNum(String insureNum) {
        this.insureNum = insureNum;
    }

    public String getOnlinePaymentId() {
        return onlinePaymentId;
    }

    public void setOnlinePaymentId(String onlinePaymentId) {
        this.onlinePaymentId = onlinePaymentId == null ? null : onlinePaymentId.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg == null ? null : failMsg.trim();
    }

    public String getNewInsureNum() {
        return newInsureNum;
    }

    public void setNewInsureNum(String newInsureNum) {
        this.newInsureNum = newInsureNum == null ? null : newInsureNum.trim();
    }

    public String getCancelInsurants() {
        return cancelInsurants;
    }

    public void setCancelInsurants(String cancelInsurants) {
        this.cancelInsurants = cancelInsurants == null ? null : cancelInsurants.trim();
    }

    public String getCancelMsg() {
        return cancelMsg;
    }

    public void setCancelMsg(String cancelMsg) {
        this.cancelMsg = cancelMsg == null ? null : cancelMsg.trim();
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }


}