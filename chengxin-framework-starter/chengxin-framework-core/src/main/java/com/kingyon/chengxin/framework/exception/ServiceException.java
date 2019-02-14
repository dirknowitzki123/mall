package com.kingyon.chengxin.framework.exception;

/**
 * 请求无效异常
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 1690624130527369260L;

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceException(ErrorCode error, String msg) {
		super(error, error.getCode() + ":" + msg);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(ErrorCode error) {
		super(error, error.getCode() + ":" + error.getMessage());
		// TODO Auto-generated constructor stub
	}
	
	public ServiceException(ErrorCode error, String msg, Object data) {
		super(error, error.getCode() + ":" + msg, data);
	}

    
}
