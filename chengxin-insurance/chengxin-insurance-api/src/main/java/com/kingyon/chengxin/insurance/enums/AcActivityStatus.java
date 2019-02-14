package com.kingyon.chengxin.insurance.enums;

/**
 * @Auther: Aspen
 * @Date: 2018/9/18 0018 13:19
 */
public enum AcActivityStatus {

//支付状态
    WAIT_PAY((byte)0,"未支付"),
    PAYING((byte)1,"支付中"),
    FAIL((byte)2,"支付失败"),
    PAYED((byte)3,"已支付");

    private Byte code;
    private String  message;

    AcActivityStatus(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
