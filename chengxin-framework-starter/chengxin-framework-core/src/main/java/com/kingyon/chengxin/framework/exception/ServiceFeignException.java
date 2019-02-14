/**
 *
 */
package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 异常基类
 * @ClassName: BaseException
 *
 */
public class ServiceFeignException extends RuntimeException  {
    private static final long serialVersionUID = 5692243177785821696L;

    private int error ;
    private Object data = "";

    public ServiceFeignException(int error) {
        this(error, "", "");
    }

    public ServiceFeignException(int error, String msg) {
        this(error, msg, "");
    }

    public ServiceFeignException(int error, String msg, Object data) {
        super(msg );
    	this.error = error;
    	this.data = data;
    }

    public int getError() {
        return error;
    }


	public Object getData() {
		return data;
	}

}
