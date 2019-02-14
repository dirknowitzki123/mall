package com.kingyon.chengxin.insurance;


import com.kingyon.chengxin.framework.exception.ErrorCode;

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum InsuranceErrorCode implements ErrorCode {

    NOT_FOUND_USER_ERROR(1,"用户不存在"),
    NOT_FOUND_BARGAIN_NUM(2,"交易号不存在"),
    NOT_FOUND_PRODUCT(4,"产品不存在或已下架"),
    NO_BING_PHONE(5,"用户未绑定手机号"),
    THIRD_ERROR(3,"第三方调用错误!");
    /**
     * 返回与错误码为10开始
     */
    private Integer baseCode = 10;
    private Integer code;
    private String message;

    InsuranceErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    InsuranceErrorCode(Integer code) {
        this.code = code;
    }

    public static String getMsg(int code) {
        for (InsuranceErrorCode errorCode : InsuranceErrorCode.values()) {
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

    public InsuranceErrorCode setMessageReturnThis(String message) {
        this.message = message;
        return this;
    }

}
