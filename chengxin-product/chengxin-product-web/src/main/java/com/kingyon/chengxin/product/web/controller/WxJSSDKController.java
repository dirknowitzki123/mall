package com.kingyon.chengxin.product.web.controller;

import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.cache.redis.RedisUtil;
import com.kingyon.chengxin.product.web.wxutil.WechatSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/15 0015.
 */


@RestController
@RequestMapping("/api/weixin/")
public class WxJSSDKController {

    private static final Logger logger = LoggerFactory.getLogger(WxJSSDKController.class);
    private static final String JSAPI_TICKET_CACHE="jsapi_ticket_cache";

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("sdkConfig")
    public Response sdkConfig(@RequestParam("currentUrl")String currentUrL){

        String ticket=null;
        if (redisUtil.exists(JSAPI_TICKET_CACHE)) {
            ticket = (String)redisUtil.get(JSAPI_TICKET_CACHE);
        }else {
            ticket = WechatSendUtil.getJsapiTicket();
            redisUtil.set(JSAPI_TICKET_CACHE,ticket,  60L*55*2);
        }
        Map<String, Object> wxConfigSignature = WechatSendUtil.getWXConfigSignature(currentUrL, ticket);

        return new Response(wxConfigSignature);
    }


}
