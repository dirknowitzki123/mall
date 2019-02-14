package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

public class OmOrder extends BaseEntity {
    /**
     * 订单号
     * @time 2018-10-23 16:26:00
     */
    private String orderNumber;

    /**
     * WX微信Ali支付宝Other其他方式
     * @time 2018-10-23 16:26:00
     */
    private String payWay;

    /**
     * 1已购买
     * @time 2018-10-23 16:26:00
     */
    private Integer status;

    /**
     * 关联用户账户表
     * @time 2018-10-23 16:26:00
     */
    private Long buyAccount;

    /**
     * 总金额
     * @time 2018-10-23 16:26:00
     */
    private BigDecimal amount;

    /**
     * 下单时间
     * @time 2018-10-23 16:26:00
     */
    private Date orderTime;

    /**
     * 发票号
     * @time 2018-10-23 16:26:00
     */
    private String invoice;

    /**
     * 纳税人
     * @time 2018-10-23 16:26:00
     */
    private String taxpayer;

    /**
     * 纳税人识别号
     * @time 2018-10-23 16:26:00
     */
    private String taxpayerNumber;

    /**
     * 发票照片
     * @time 2018-10-23 16:26:00
     */
    private String invoiceImage;

    /**
     * 关联后台账户表
     * @time 2018-10-23 16:26:00
     */
    private Long operator;

    /**
     * 支付状态
     * @time 2018-10-23 16:26:00
     */
    private Integer payStatus;

    /**
     * 支付时间
     * @time 2018-10-23 16:26:00
     */
    private Date payTime;

    private String remark;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(Long buyAccount) {
        this.buyAccount = buyAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice == null ? null : invoice.trim();
    }

    public String getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(String taxpayer) {
        this.taxpayer = taxpayer == null ? null : taxpayer.trim();
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber == null ? null : taxpayerNumber.trim();
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage == null ? null : invoiceImage.trim();
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}