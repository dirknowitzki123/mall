package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class OmOrderEvaluate extends BaseEntity {
    /**
     * 关联账户表
     * @time 2018-10-17 10:30:25
     */
    private Long evaluateUserId;

    /**
     * 评价内容
     * @time 2018-10-17 10:30:25
     */
    private String content;

    /**
     * 1就诊2保险3体检4基因5其他6套餐
     * @time 2018-10-17 10:30:25
     */
    private Integer projectType;

    /**
     * 根据项目类型关联相应项目id
     * @time 2018-10-17 10:30:25
     */
    private Long evaluateProjectId;

    /**
     * 0否1是
     * @time 2018-10-17 10:30:25
     */
    private Boolean visible;

    /**
     * 0否1是
     * @time 2018-10-17 10:30:25
     */
    private Boolean userDemand;

    /**
     * 0否后台添加1是后台添加
     * @time 2018-10-17 10:30:25
     */
    private Boolean evaluateType;

    /**
     * 评分
     * @time 2018-10-17 10:30:25
     */
    private Double score;

    /**
     * 关联订单表中的订单号
     * @time 2018-10-17 10:30:25
     */
    private Long workorderId;

    /**
     * 关联工单表中的订单号
     * @time 2018-10-17 10:30:25
     */
    private Long orderId;

    /**
     * 关联后台账户表
     * @time 2018-10-17 10:30:25
     */
    private Long operator;


    private String fakeAccount;

    private String fakePrc;

    /**
     * 是否匿名
     * @time 2018-10-17 10:30:25
     */
    private Boolean anonymity;

    public Long getEvaluateUserId() {
        return evaluateUserId;
    }

    public void setEvaluateUserId(Long evaluateUserId) {
        this.evaluateUserId = evaluateUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Long getEvaluateProjectId() {
        return evaluateProjectId;
    }

    public void setEvaluateProjectId(Long evaluateProjectId) {
        this.evaluateProjectId = evaluateProjectId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getUserDemand() {
        return userDemand;
    }

    public void setUserDemand(Boolean userDemand) {
        this.userDemand = userDemand;
    }

    public Boolean getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Boolean evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getFakeAccount() {
        return fakeAccount;
    }

    public void setFakeAccount(String fakeAccount) {
        this.fakeAccount = fakeAccount == null ? null : fakeAccount.trim();
    }

    public String getFakePrc() {
        return fakePrc;
    }

    public void setFakePrc(String fakePrc) {
        this.fakePrc = fakePrc == null ? null : fakePrc.trim();
    }

    public Boolean getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(Boolean anonymity) {
        this.anonymity = anonymity;
    }
}