package com.kingyon.chengxin.product.web.interceptor;


import com.kingyon.chengxin.framework.SysErrorCode;
import com.kingyon.chengxin.framework.annotation.NoAuthorize;
import com.kingyon.chengxin.framework.cache.redis.RedisUtil;
import com.kingyon.chengxin.framework.config.AppConfig;
import com.kingyon.chengxin.framework.exception.SysException;
import com.kingyon.chengxin.framework.util.ObjectSerializer;
import com.kingyon.chengxin.framework.web.HttpContext;
import com.kingyon.chengxin.product.ProductErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by leo on 16/6/13.
 * Web 用户认证/登录状态拦截器
 */
//@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String isAuth = AppConfig.getProperty("common.isAuth");

        String from_ip = HttpContext.getRemoteAddr(request);
        String uri = request.getRequestURI();
        HandlerMethod handlerMethod = null;
        //处理swagger的handler
        if (!(handler instanceof ResourceHttpRequestHandler)) {
            handlerMethod = (HandlerMethod) handler;
        }
        String appKey = request.getHeader("XAuthToken");
        if (uri.contains("/api/") && isAuth.equals("true")) {
            if (handlerMethod.hasMethodAnnotation(NoAuthorize.class)) {
                return true;
            }
            if (appKey == null) {
                throw new SysException(SysErrorCode.NO_LOGIN);
            }
            Long accid = (Long) redisUtil.get(appKey);
            if (null == accid) {
                throw new SysException(SysErrorCode.NO_LOGIN);
            }
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        /**访问统计*/
    }


}
