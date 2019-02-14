/**
 *
 */
package com.kingyon.chengxin.framework.exception;

import com.google.common.base.Strings;
import com.kingyon.chengxin.framework.SysErrorCode;

/**
 * @Description: 异常基类
 * @ClassName: BaseException
 *
 */
public class BaseException extends RuntimeException implements ExceptionScalable {
    private static final long serialVersionUID = 5692243177785821696L;

    private ErrorCode error ;
    private Object data = "";

    public BaseException() {
        error = SysErrorCode.SERVICE_ERROR;
    }

    public BaseException(ErrorCode error) {
        this(error, error.getMessage(), "");
    }

    public BaseException(ErrorCode error, String msg) {
        this(error, msg, "");
    }
    
    public BaseException(ErrorCode error, String msg, Object data) {
        super(msg );
    	this.error = error;
    	String cur_msg = msg;
        if (!Strings.isNullOrEmpty(cur_msg)) {
            int idx = cur_msg.lastIndexOf(":");
            if (idx >= 0) {
                cur_msg = cur_msg.substring(idx + 1);
            }
            this.error.setMessage( cur_msg);
        }

    	this.data = data;
    }

    @Override
    public ErrorCode getError() {
        return error;
    }

	@Override
	public Object getData() {
		return data;
	}

}
