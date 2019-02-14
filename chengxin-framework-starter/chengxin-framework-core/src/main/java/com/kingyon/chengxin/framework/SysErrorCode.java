package com.kingyon.chengxin.framework;


import com.kingyon.chengxin.framework.exception.ErrorCode;

/**
 * Created by Administrator on 16-9-14.
 */
public enum SysErrorCode implements ErrorCode {

	SUCCESS(0, "操作成功!"), 
	FAIL(1, "操作失败!"), 
	REQ_ERROR(400, "请求错误!"), 
	SERVICE_ERROR(500, "内部服务错误!"),
    OBJECT_COPY_ERROR(501, "对象复制错误!"),

    AUTH_FAIL(401, "授权失败或超时,请登录访问!"),
	TIMEOUT(408, "请求超时!"),
	REQ_PARAMS_ERROR(406, "请求参数错误!"),

    ONE_LOGIN(420, "您已经在另一台手机登录"),
    SENS_WORD(421, "提交的数据中包含敏感字符，请重试！"),
    
    SERVICE_STOP(800, "该服务已暂时停用！"),

    NO_AUTH_REQUEST(422, "无效请求,校验错误"),
    ;
    private Integer code;
    private String message;

    SysErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    SysErrorCode(Integer code) {
        this.code = code;
    }
    
    public static String getMsg(int code) {
    	for(SysErrorCode errorCode : SysErrorCode.values()) {
    		if(code == errorCode.getCode()) {
    			return errorCode.getMessage();
    		}
    	}
    	return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

	@Override
	public void setMessage(String msg) {
		this.message = msg;
	}

}
