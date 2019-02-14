package com.kingyon.chengxin.product.web.wxutil;

import com.alibaba.fastjson.JSONObject;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.framework.net.OkHttpRest;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.dto.weixin.ThirdLoginUser;
import com.kingyon.chengxin.product.dto.weixin.WechatDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * describe：消息发送工具类
 *
 * @author jzc
 * @since 0.1.0
 */
@Slf4j
public class WechatSendUtil {


    private static final String CONTENT_TYPE = "Content-Type";
    static OkHttpRest okHttpRest = OkHttpRest.getHttpClient("wx_request");

    static {
        okHttpRest.addHeader(CONTENT_TYPE, " application/x-www-form-urlencoded");
        okHttpRest.addHeader("Accept", "application/json");
    }

    /**
     * 发送消息url
     */
    public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESSTOKEN";
    /**
     * 获得发送消息用的accessToken
     */
    public static final String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRE";
    /**
     * 根据code获得accessToken、用户的openid
     */
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID_&secret=SECRET_&code=CODE_&grant_type=authorization_code";

    /**
     * 根据accessToken刷新TOKEN
     */
    public static final String REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID_&grant_type=refresh_token&refresh_token=REFRESH_TOKEN_";
    /**
     * 根据accessToken、openid获得微信用户基本信息
     */
    public static final String USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN_&openId=OPENID_&lang=zh_CN";

    /**
     * 获取JSAPI_TICKET
     */
    public static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";


    /**
     * 获得发送消息用的accessToken
     *
     * @return accessToken
     */
    public static String getAccesstoken() {
        String url = GET_ACCESSTOKEN.replace("APPID", PropsValue.WECHAT_APPID).replace("APPSECRE", PropsValue.WECHAT_APP_SECRET);
        String jsonObject = okHttpRest.doGet(url, String.class);
        Map object = Converter.parseObject(jsonObject, Map.class);
        log.info("获取accesstoken返回的json字符串：{}", object.toString());
        Object token = object.get("access_token");
        if (null == token) {
            return "";
        }
        String accessToken = object.get("access_token").toString();
        return accessToken;
    }


    /**
     * 根据微信用户的code获得accessToken
     *
     * @param code
     * @return
     */
    public static WechatDto getAccessTokenByCode(String code) {
        String url = ACCESS_TOKEN;
        url = url.replace("APPID_", PropsValue.WECHAT_APPID);
        url = url.replace("SECRET_", PropsValue.WECHAT_APP_SECRET);
        url = url.replace("CODE_", code);
        return getWechatDto(url);
    }

    /**
     * 刷新access_token
     * 官方文档中提到了刷新access_token的功能，但这不是必须要做的，初次使用可以先忽略。
     *
     * @param token
     * @return
     */
    public static WechatDto getRefreshToken(String token) {
        String url = REFRESH_TOKEN;
        url = url.replace("APPID_", PropsValue.WECHAT_APPID);
        url = url.replace("REFRESH_TOKEN_", token);
        return getWechatDto(url);
    }

    /**
     * 根据路径获得微信信息
     *
     * @param url
     * @return
     */
    private static WechatDto getWechatDto(String url) {
        String res = okHttpRest.doGet(url, String.class);
        Map object = Converter.parseObject(res, Map.class);

        log.info("Accesstoken: " + object);
        if (object != null) {
            if (!StringUtils.isEmpty(object.get("errcode"))) {
                String errorMsg = (String) object.get("errmsg");
                throw new WebException(ProductErrorCode.THIRD_ERROR, errorMsg.replace(":", "-"));
            }
            WechatDto wechatDto = new WechatDto();
            if (!StringUtils.isEmpty(object.get("access_token"))) {
                wechatDto.setAccessToken((String) object.get("access_token"));
            }
            if (!StringUtils.isEmpty(object.get("refresh_token"))) {
                wechatDto.setRefreshToken((String) object.get("refresh_token"));
            }
            if (!StringUtils.isEmpty(object.get("openid"))) {
                wechatDto.setOpenID((String) object.get("openid"));
            }
            return wechatDto;
        }
        return null;
    }

    /**
     * 根据accessToken、openID获得微信用户信息
     *
     * @param accessToken
     * @param openID
     * @return
     */
    public static ThirdLoginUser getUserInfo(String accessToken, String openID) {
        String url = USER_INFO;
        url = url.replace("ACCESS_TOKEN_", accessToken);
        url = url.replace("OPENID_", openID);

        String resultStr = okHttpRest.doGet(url, String.class);
        Map<String, Object> object = Converter.parseObject(resultStr, Map.class);
        if (!StringUtils.isEmpty(object.get("errcode"))) {
            String errorMsg = (String) object.get("errmsg");
            throw new WebException(ProductErrorCode.THIRD_ERROR, errorMsg.replace(":", "-"));
        }

        if (object != null) {
            ThirdLoginUser wechatUser = new ThirdLoginUser();
            wechatUser.setNickName((String) object.get("nickname"));
            wechatUser.setOpenId((String) object.get("openId"));
            wechatUser.setHead((String) object.get("headimgurl"));
            wechatUser.setSex((Integer) object.get("sex"));
            wechatUser.setOpenId(openID);
            return wechatUser;
        }
        return null;
    }

    public static String getJsapiTicket() {
        String accesstoken = getAccesstoken();
        String requstUrl = JSAPI_TICKET.replace("ACCESS_TOKEN", accesstoken);
        log.info("url:" + requstUrl);
        String res = okHttpRest.doGet(requstUrl, String.class);
        Map object = Converter.parseObject(res, Map.class);
        log.info("获取JsapiTicket返回的json字符串：" + object.toString());
        return (String) object.get("ticket");
    }

    ;

    public static Map<String, Object> getWXConfigSignature(String url, String ticket) {
        long timeStampSec = System.currentTimeMillis() / 1000;
        String timestamp = String.format("%010d", timeStampSec);
        String nonceStr =  UUID.randomUUID().toString().replace("-", "");;
        Map<String, Object> resp = new HashMap<String, Object>();
        String[] signArr = new String[] { "url=" + url, "jsapi_ticket=" + ticket, "noncestr=" + nonceStr,
                "timestamp=" + timestamp };
        Arrays.sort(signArr);
        String signStr = StringUtils.arrayToDelimitedString(signArr, "&");
        String resSign = DigestUtils.sha1Hex(signStr);
        resp.put("appId", PropsValue.WECHAT_APPID);
        resp.put("timestamp", timestamp);
        resp.put("nonceStr", nonceStr);
        resp.put("signature", resSign);
        return resp;
    }


}
