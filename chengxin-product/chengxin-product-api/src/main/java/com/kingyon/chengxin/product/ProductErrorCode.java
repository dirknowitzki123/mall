package com.kingyon.chengxin.product;


import com.kingyon.chengxin.framework.exception.ErrorCode;

public enum ProductErrorCode implements ErrorCode {

    FAIL_UPLOAD(1,"上传文件失败,不支持的文件类型"),
    NOT_FOUND_PRODUCT(2,"未找到对应的产品"),
    THIRD_ERROR(3, "三方接口调用失败"),
    SAVE_FAIL(4,"保存失败"),
    HEALTH_STATEMENT(5,"用户需填写健康告知"),
    NOT_FOUND_ORDER(6,"未找到对应订单信息"),
    PRICE_ERROR(7,"支付金额有误"),
    NOT_FOUND_USER_ACCOUNT(8,"未找到相关用户信息"),
    PRODUCT_TYPE_NOT_MATCH(9,"产品类型不匹配"),
    NOT_FOUND_SKU(10,"未找到对应的SKU信息"),
    NOT_ENOUGH_SKU(11,"该产品型号库存量不足"),
    ;
    /**
     * 返回与错误码为10开始
     */
    private Integer baseCode = 10;
    private Integer code;
    private String message;

    ProductErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ProductErrorCode(Integer code) {
        this.code = code;
    }

    public static String getMsg(int code) {
        for (ProductErrorCode errorCode : ProductErrorCode.values()) {
            if (code == errorCode.getCode()) {
                return errorCode.getMessage();
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return baseCode * 1000 + code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public ProductErrorCode setMessageReturnThis(String message) {
        this.message = message;
        return this;
    }

}
