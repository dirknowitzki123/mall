package com.kingyon.chengxin.product.modal;

import com.kingyon.chengxin.framework.modal.BaseEntity;

public class OmOrderStatusRec extends BaseEntity {
    /**
     * 订单号
     * @time 2019-01-04 11:38:05
     */
    private String orderNumber;

    /**
     * 订单状态
     * @time 2019-01-04 11:38:05
     */
    private Integer status;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}