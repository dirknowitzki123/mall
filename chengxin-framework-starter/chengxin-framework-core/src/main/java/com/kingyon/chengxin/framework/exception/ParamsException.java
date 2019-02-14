package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 参数异常
 * @ClassName: ParamsException
 *
 */
public class ParamsException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137006668984255381L;

	public ParamsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParamsException(ErrorCode error, String msg) {
		super(error, msg);
		// TODO Auto-generated constructor stub
	}

	public ParamsException(ErrorCode error) {
		super(error);
		// TODO Auto-generated constructor stub
	}

	
}
