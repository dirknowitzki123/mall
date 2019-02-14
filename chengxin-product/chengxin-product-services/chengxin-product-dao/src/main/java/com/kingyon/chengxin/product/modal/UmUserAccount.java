package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class UmUserAccount extends BaseEntity {
    /**
     * 昵称
     * @time 2018-09-18 11:05:29
     */
    private String nickName;

    /**
     * 账号
     * @time 2018-09-18 11:05:29
     */
    private String accountName;

    /**
     * 姓名
     * @time 2018-09-18 11:05:29
     */
    private String userName;

    /**
     * 身份证号
     * @time 2018-09-18 11:05:29
     */
    private String idcard;

    /**
     * 性别
     * @time 2018-09-18 11:05:29
     */
    private Integer sex;

    /**
     * 密码
     * @time 2018-09-18 11:05:29
     */
    private String password;

    /**
     * 手机号
     * @time 2018-09-18 11:05:29
     */
    private String phone;

    /**
     * 购买次数
     * @time 2018-09-18 11:05:29
     */
    private Integer payTotal;

    /**
     * 来源渠道
     * @time 2018-09-18 11:05:29
     */
    private String sources;

    /**
     * 来源方式
     * @time 2018-09-18 11:05:29
     */
    private String sourceWay;

    /**
     * 会员等级
     * @time 2018-09-18 11:05:29
     */
    private Short level;

    /**
     * 爱心值
     * @time 2018-09-18 11:05:29
     */
    private Integer loveValue;

    /**
     * 健康豆
     * @time 2018-09-18 11:05:29
     */
    private Integer healthBeans;

    /**
     * 微信公众号上的openid
     * @time 2018-09-18 11:05:29
     */
    private String openId;


    /**
     * 备注
     * @time 2018-09-18 11:05:29
     */
    private String remark;

    /**
     * 关联后台账户表
     * @time 2018-09-18 11:05:29
     */
    private Long operator;

    private String headUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Integer payTotal) {
        this.payTotal = payTotal;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources == null ? null : sources.trim();
    }

    public String getSourceWay() {
        return sourceWay;
    }

    public void setSourceWay(String sourceWay) {
        this.sourceWay = sourceWay == null ? null : sourceWay.trim();
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Integer getLoveValue() {
        return loveValue;
    }

    public void setLoveValue(Integer loveValue) {
        this.loveValue = loveValue;
    }

    public Integer getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(Integer healthBeans) {
        this.healthBeans = healthBeans;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }
}