package com.kingyon.chengxin.product;

import com.kingyon.chengxin.framework.exception.ErrorCode;

public enum OrderErrorCode implements ErrorCode {
	ORDER_REFUND_STATUS_INPUT_ERROR(1,"退款状态输入错误"),
	USER_ACCOUNT_INFO_NOT_EXIST(2,"用户账户信息不存在"),
	ORDER_INFO_NOT_EXIST(3,"订单不存在"),
	ORDER_AMOUNT_NOT_MATCH(4,"微信回调订单金额不匹配"),
	;

	/**
	 * 返回与错误码为10开始
	 */
	private Integer baseCode = 10;
	private Integer code;
	private String message;

	OrderErrorCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	OrderErrorCode(Integer code) {
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

	public OrderErrorCode setMessageReturnThis(String message) {
		this.message = message;
		return this;
	}
}
