package com.kingyon.chengxin.product.enums;

/**
 * @Auther: Aspen
 * @Date: 2018/10/16 0016 13:19
 */
public enum ChannelType {

    QX_HUIZHE(3340001,"齐欣慧择"),
    TK_TAIKANG(3340002,"泰康"),
    XRK_XIANGRIKUI(3340003,"向日葵");

    private Integer code;
    private String  message;

    ChannelType(Integer code, String message) {
        this.code =code;
        this.message= message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
