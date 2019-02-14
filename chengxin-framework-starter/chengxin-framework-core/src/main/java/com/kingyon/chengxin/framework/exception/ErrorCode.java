package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 错误返回码接口
 * @ClassName: ErrorCode
 *
 */
public interface ErrorCode {
	
	/**
	 * 返回码
	 * @return
	 */
	int getCode();

	/**
	 * 异常信息, 源自{@link Throwable#getMessage()}
	 * 
	 * @return
	 */
	String getMessage();
	
	void setMessage(String msg);

}
