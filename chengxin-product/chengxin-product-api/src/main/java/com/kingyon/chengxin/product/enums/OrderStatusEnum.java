package com.kingyon.chengxin.product.enums;

/**
 * @author <a href="pigeon@kingyon.com">allen</a>
 * @datetime 2018/6/6
 */
public enum OrderStatusEnum {
    UNPAY(0,"未付款"),
    UNUSED(1,"待使用"),
    EVALUATED(2,"未评价"),
    FINISHED(3,"已完成"),

    /* 以下码值适用于在线商城项目，0-3这四个码值为了避免和历史数据混淆所以不再使用  */
    ORDER_UNPAIED(10,"待付款"),
    ORDER_CANCELD(11,"已取消"),
    UNDELIVER(12,"待发货"),
    REFUNDING(13,"退款中/待退款"),
    REFUNDED(14,"已退款"),
    UNTAKE_GOODS(15,"待收货"),
    GOODS_RETURNING(16,"退货中/待退货"),
    GOODS_RETURN_FAIL(17,"退货失败/拒绝退货"),
    TAKEN_GOODS(18,"已收货"),
    GOODS_RETURN_FINISH(19,"退货完成"),
    ;
    private Integer status;
    private String display;
    OrderStatusEnum(Integer status, String display){
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

    public static void main(String[] args) {

    }
}
