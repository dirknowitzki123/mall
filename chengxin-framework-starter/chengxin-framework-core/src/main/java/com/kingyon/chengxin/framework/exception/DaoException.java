package com.kingyon.chengxin.framework.exception;

/**
 * 请求无效异常
 * Created by taoping on 2016/8/16.
 */
public class DaoException extends BaseException {
    private static final long serialVersionUID = 1690624130527369260L;

	public DaoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DaoException(ErrorCode error, String msg) {
		super(error, error.getCode() + ":" + msg);
		// TODO Auto-generated constructor stub
	}

	public DaoException(ErrorCode error) {
		super(error, error.getCode() + ":"  + error.getMessage());
		// TODO Auto-generated constructor stub
	}

    
}
