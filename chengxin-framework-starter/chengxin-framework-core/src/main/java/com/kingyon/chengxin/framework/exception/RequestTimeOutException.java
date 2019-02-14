package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 请求超时
 * @ClassName: RequestOutTime
 *
 */
public class RequestTimeOutException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6634469533564245709L;

	public RequestTimeOutException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestTimeOutException(ErrorCode error, String msg) {
		super(error, msg);
		// TODO Auto-generated constructor stub
	}

	public RequestTimeOutException(ErrorCode error) {
		super(error);
		// TODO Auto-generated constructor stub
	}
	

}
