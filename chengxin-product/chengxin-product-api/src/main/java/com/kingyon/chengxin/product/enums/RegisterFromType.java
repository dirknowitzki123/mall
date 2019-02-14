package com.kingyon.chengxin.product.enums;

public enum RegisterFromType {
    APP(1, "APP"),
    QQ(2, "QQ"),
    WECHAT(3, "微信"),
    WEBSITE(4, "网站"),
    ERP(5,"ERP导入");

    private String display;

    private Integer status;


    RegisterFromType(Integer status, String display) {
        this.display = display;
        this.status = status;
    }

    public static RegisterFromType getEnum(String value) {
        for (RegisterFromType v : values()) {
            if (v.getDisplay().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getDisplay() {
        return display;
    }


    public Integer getStatus() {
        return status;
    }
}
