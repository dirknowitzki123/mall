package com.kingyon.chengxin.product.enums;

/**
 * @author Aspen
 * @Date: 2018/10/23 0023 16:56
 */
public enum PayStatusEnum {

    UNPAID(0,"待支付"),
    PAID(1,"已支付"),
    CANCEL(2,"已取消"),
    REFUNDED(3,"已退款");
    private Integer status;
    private String display;
    PayStatusEnum(Integer status, String display){
        this.status = status;
        this.display = display;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public static String getMessage(Integer value) {
        PayStatusEnum[] payStatus = values();
        for (PayStatusEnum prodTypeEnum : payStatus) {
            if (prodTypeEnum.getStatus().equals(value)) {
                return prodTypeEnum.getDisplay();
            }
        }
        return null;
    }


}
