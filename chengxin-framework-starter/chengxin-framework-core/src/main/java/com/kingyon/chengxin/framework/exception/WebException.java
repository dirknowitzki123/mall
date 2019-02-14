package com.kingyon.chengxin.framework.exception;

/**
 * 请求无效异常
 * Created by taoping on 2016/8/16.
 */
public class WebException extends BaseException {
    private static final long serialVersionUID = 1690624130527369260L;

	public WebException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebException(ErrorCode error, String msg) {
		super(error, msg);
		// TODO Auto-generated constructor stub
	}

	public WebException(ErrorCode error) {
		super(error);
		// TODO Auto-generated constructor stub
	}

	public WebException(ErrorCode error, String msg, Object data) {
		super(error, msg, data);
	}

    
}
