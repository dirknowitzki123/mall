package com.kingyon.chengxin.framework.web.interceptor;

import com.alibaba.dubbo.remoting.TimeoutException;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Description: 统计异常处理
 * @ClassName: GlobalExceptionHandler
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Http请求时，参数异常   400
     *
     * @param
     * @param e
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Response handlerRequstParams(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        printRequest(request);
        log.error("MethodArgumentTypeMismatchException:" + e.getMessage(), e);
        return Response.error(400);
    }

    /**
     * Http请求时，参数异常   400
     *
     * @param
     * @param e
     */
    @ExceptionHandler(ServletException.class)
    @ResponseBody
    public Response ServletException(ServletException e, HttpServletRequest request) {
        printRequest(request);
        log.error("ServletException:" + e.getMessage(), e);
        return Response.error(400);
    }

    /**
     * 服务器内部出错	500
     *
     * @param
     * @param e
     */
    @ExceptionHandler(SysException.class)
    @ResponseBody
    public Response SysException(SysException e, HttpServletRequest request) {
        printRequest(request);
        log.error("SysException:" + e.getMessage(), e);
        return new Response(e.getError().getCode(), e.getError().getMessage()); //Response.error(500);
    }

    /**
     * 请求超时		408
     *
     * @param
     * @param e
     */
    @ExceptionHandler(TimeoutException.class)
    @ResponseBody
    public Response RequestTimeOutException(RequestTimeOutException e, HttpServletRequest request) {
        printRequest(request);
        log.error("TimeOutException:" + e.getMessage(), e);
        return Response.error(408);
    }

    /**
     * 参数出错	406
     *
     * @param
     * @param e
     */
    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public Response ParamsException(ParamsException e, HttpServletRequest request) {
        printRequest(request);
        log.error("ParamsException:" + e.getMessage(), e);
        return new Response(406, e.getData()); //Response.error(406);
    }


    /**
     * 参数出错	406
     *
     * @param
     * @param e
     */
    @ExceptionHandler( MissingServletRequestParameterException.class)
    @ResponseBody
    public Response MissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        printRequest(request);
        log.error("ParamsException:" + e.getMessage(), e);
        return new Response(406, "参数错误!"); //Response.error(406);
    }

    /**
     * 参数出错	406
     *
     * @param
     * @param e
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Response HttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        printRequest(request);
        log.error("JsonParseException:" + e.getMessage(), e);
        return new Response(406,"JSON参数封装错误"); //Response.error(406);
    }



    /**
     * 业务统一异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Response BaseException(HttpServletRequest request, BaseException e) {
        printRequest(request);
        log.error("BaseException: errorCode:" + e.getError().getCode() + "[" + e.getError().getMessage() + "]" + e.getData(), e);
        return new Response(e.getError().getCode(), e.getError().getMessage());
    }



    /**
     * 服务发现前端异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceFeignException.class)
    @ResponseBody
    public Response serviceFeighException(HttpServletRequest request, ServiceFeignException e) {
        printRequest(request);
        log.error("serviceFeighException errorCode:" + e.getError() + "[" + e.getMessage() + "]" + e.getData(), e);
        return new Response(e.getError(), e.getMessage());
    }

    /**
     * 统一未知异常处理		500
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleGlobal(HttpServletRequest request, Exception e) {
        printRequest(request);
        log.error("Exception:" + e.getMessage(), e);
        if (e.getMessage() != null && e.getMessage().startsWith("com.kingyon.chengxin.framework.exception.")) {
            try {
                String[] msg = e.getMessage().split(":");
                int code = Integer.parseInt(msg[1].trim());
                String[] errorMsg = msg[2].split("\n");
                return new Response(code,errorMsg[0]);
            } catch (NumberFormatException e1) {

            }
        }
        return new Response(500);
    }

    /**
     * 请求参数格式异常处理	406
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response MethodArgumentNotValidHandler(MethodArgumentNotValidException exception, HttpServletRequest request) throws Exception {
        printRequest(request);
        BindingResult result = exception.getBindingResult();
        log.error("MethodArgumentNotValidHandler:" + exception.getMessage(), exception);
        return bindParamValid(result);
    }

    /**
     * 请求参数格式异常处理	406
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Response BindException(BindException exception, HttpServletRequest request) throws Exception {
        printRequest(request);
        BindingResult result = exception.getBindingResult();
        log.error("BindException:" + exception.getMessage(), exception);
        return bindParamValid(result);
    }

    /**
     * 数据绑定参数校验
     *
     * @param result
     * @return
     */
    private Response bindParamValid(BindingResult result) {
        List<Object> invalidArguments = new ArrayList<Object>();
        Response response = new Response(406);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("filed", error.getField());
                map.put("errorMsg", error.getDefaultMessage());
                invalidArguments.add(map);
                response.message = error.getDefaultMessage();
                return response;

            }
        }
        return response;
    }

    /**
     * 输出异常请求信息
     *
     * @param request
     */
    public void printRequest(HttpServletRequest request) {
        StringBuffer buf = new StringBuffer("request err! url:");

        buf.append(request.getRequestURL().toString())
                .append("\nheader:\n");

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            buf.append("\t").append(header + "=" + request.getHeader(header) + "\n");
        }

        buf.append("params:\n");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = (String) params.nextElement();
            buf.append("\t").append(param + "=" + request.getParameter(param) + "\n");
        }
        log.error(buf.toString());
    }


}
