package com.kingyon.chengxin.framework.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 16-11-9.
 */
@Slf4j
public class HttpContext {

    /**
     * 获得请求用户的真实IP地址
     * @param request
     * @return
     */
    public static final String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("x-real-ip");
        }
//        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
//            ip=request.getHeader("WL-Proxy-Client-IP");
//        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
//        log.info("remoteAddr {}, {}" , ip , request.getRemoteAddr());
        if (ip != null) {
            String[] ips = ip.split(",");
            for(String cur : ips ) {
                ip = cur.trim();
                if (ip.length() > 0 && !"unknown".equals(ip))
                    break;
            }
        }

        return ip;
    }
}
