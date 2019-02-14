package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 系统内部异常
 * @ClassName: sysException
 *
 */
public class SysException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6878142870884828535L;

	public SysException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysException(ErrorCode error, String msg) {
		super(error, msg);
		// TODO Auto-generated constructor stub
	}

	public SysException(ErrorCode error) {
		super(error);
		// TODO Auto-generated constructor stub
	}


}
