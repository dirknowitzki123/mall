package com.kingyon.chengxin.framework;

import com.kingyon.chengxin.framework.exception.ErrorCode;

/**
 * Created by leo on 16/6/6.
 */

public class Response<T> extends AbstractEntity {


    /**
     * code 200 - 正确； 非200为错误码
     */
    public int code = 200;

    public String message = "";

    public T data ;
    
    public Response(ErrorCode errorCode) {
    	this.code = errorCode.getCode();
    	this.message = errorCode.getMessage();
    }
    
    public Response(ErrorCode errorCode, T data) {
    	this.code = errorCode.getCode();
    	this.message = errorCode.getMessage();
    	this.data = data;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response() {
    }

    public Response(int code) {
        this(code, SysErrorCode.getMsg(code), null);
    }


    public Response(int code, String message) {
        this(code, message, null);
    }

    public Response(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data == null ? null : data;
    }

    public Response(int code, T data) {
        this(code, "", data);
    }

    /**
     * 根据code返回错误编码
     *
     * @param code
     * @return
     */
    public static Response error(int code) {
        return new Response(code);
    }

    public static Response error(String message) {
        Response response = new Response(500, message);
        return response;
    }

    public static Response badRequest() {
        Response response = new Response(400, "");
        return response;
    }


}
