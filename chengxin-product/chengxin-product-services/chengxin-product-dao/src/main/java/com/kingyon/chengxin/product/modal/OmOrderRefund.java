package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

public class OmOrderRefund extends BaseEntity {
    /**
     * 订单详情表编号
     * @time 2019-01-07 10:58:08
     */
    private Integer orderDetailId;

    /**
     * 退款金额
     * @time 2019-01-07 10:58:08
     */
    private BigDecimal refundMoney;

    /**
     * 退款时间
     * @time 2019-01-07 10:58:08
     */
    private Date refundTime;

    /**
     * 退款原因
     * @time 2019-01-07 10:58:08
     */
    private String refundReason;

    /**
     * 退款状态-0:拒绝; 1:同意
     * @time 2019-01-07 10:58:08
     */
    private String refundStatus;

    /**
     * 拒绝退款理由
     * @time 2019-01-07 10:58:08
     */
    private String rejectReason;

    /**
     * 删除标识
     * @time 2019-01-07 10:58:08
     */
    private Byte deleted;

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason == null ? null : refundReason.trim();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}