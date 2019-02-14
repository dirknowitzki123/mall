package com.kingyon.chengxin.insurance.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class AcDiscount extends BaseEntity {
    /**
     * 活动名称
     * @time 2018-09-18 14:07:51
     */
    private String acName;

    /**
     * 折扣
     * @time 2018-09-18 14:07:51
     */
    private Byte discount;

    /**
     * 创建人id
     * @time 2018-09-18 14:07:51
     */
    private Long operator;

    /**
     * 活动banner
     * @time 2018-09-18 14:07:51
     */
    private String acImg;

    /**
     * 状态(0启用,-1停用)
     * @time 2018-09-18 14:07:51
     */
    private Byte state;

    /**
     * 活动开始时间
     * @time 2018-09-18 14:07:51
     */
    private Date beginTime;

    /**
     * 活动结束时间
     * @time 2018-09-18 14:07:51
     */
    private Date endTime;

    /**
     * 活动限制
     * @time 2018-09-18 14:07:51
     */
    private Byte astrict;

    /**
     * 活动编码
     * @time 2018-09-18 14:07:51
     */
    private String acCode;


    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    public Byte getDiscount() {
        return discount;
    }

    public void setDiscount(Byte discount) {
        this.discount = discount;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getAcImg() {
        return acImg;
    }

    public void setAcImg(String acImg) {
        this.acImg = acImg == null ? null : acImg.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getAstrict() {
        return astrict;
    }

    public void setAstrict(Byte astrict) {
        this.astrict = astrict;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode == null ? null : acCode.trim();
    }


}