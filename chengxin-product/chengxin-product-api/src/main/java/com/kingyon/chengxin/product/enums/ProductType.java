package com.kingyon.chengxin.product.enums;

import org.apache.xmlbeans.impl.xb.ltgfmt.Code;

public enum ProductType {

    INSURANCE(10001,"保险"),
    COMBO(10002,"套餐"),
    MEDICAL(10003,"医疗服务"),
    ENTITY(10005,"实物产品");

    private Integer code;
    private String  message;

    ProductType(Integer code, String message) {
        this.code =code;
        this.message= message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(Integer value) {
        ProductType[] prodTypeEnums = values();
        for (ProductType prodTypeEnum : prodTypeEnums) {
            if (prodTypeEnum.getCode().equals(value)) {
                return prodTypeEnum.getMessage();
            }
        }
        return null;
    }
}