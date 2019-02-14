package com.kingyon.chengxin.product.web.controller;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.kingyon.chengxin.framework.ObjectCopy;
import com.kingyon.chengxin.framework.Response;
import com.kingyon.chengxin.framework.annotation.DubboReference;
import com.kingyon.chengxin.framework.annotation.NoAuthorize;
import com.kingyon.chengxin.framework.cache.redis.RedisUtil;
import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.framework.util.Converter;
import com.kingyon.chengxin.product.ProductErrorCode;
import com.kingyon.chengxin.product.api.UmUserAccountService;
import com.kingyon.chengxin.product.dto.UserAccountDto;
import com.kingyon.chengxin.product.dto.weixin.ThirdLoginUser;
import com.kingyon.chengxin.product.dto.weixin.WechatDto;

import com.kingyon.chengxin.product.enums.RegisterFromType;
import com.kingyon.chengxin.product.web.wxutil.WechatSendUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/login")
public class ThirdloginController {

    public static final Long EXPIRE_TIME = 7200L;

    @DubboReference
    UmUserAccountService userAccountService;

    @Autowired
    RedisUtil redisUtil;

    @NoAuthorize
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public Response wechat(@RequestParam @ApiParam(value = "code", required = true) String code) throws Exception {

        //根据code获得refresh_token和access_token
        WechatDto wechatDto = WechatSendUtil.getAccessTokenByCode(code);
        UserAccountDto user = userAccountService.login(wechatDto.getOpenID(), false);
        if (user == null) {
            //根据access_token和openId获得用户信息
            ThirdLoginUser wechatUser = WechatSendUtil.getUserInfo(wechatDto.getAccessToken(), wechatDto.getOpenID());
            user = userAccountService.thirdLoginCreateUser(wechatUser, RegisterFromType.WECHAT);
            //设置头像
            user.setHeadUrl(wechatUser.getHead());
        }
        Map<String, Object> result = loginInfo(user);
        return new Response(result);

    }

    //    @ApiOperation(value = "微信号和手机号绑定")
//    @PostMapping(value = "/bindWechatOpenId")
//    @JSON()
//    public RestResponseForJson<Object> bindWechatOpenId(@RequestParam(required = true) String openId,
//                                                        @RequestParam(required = true) String mobile,
//                                                        @RequestParam(required = true) String code) {
//        try {
//            //校验验证码
//            if (redisService.verifyCode(mobile, code, SMSMessage.BIND)) {
//                UmUserAccount user = null;
//                //根据unionid查找用户
//                UmUserAccount openUser = userAccountService.login(openId, false);
//                //根据手机号查找用户
//                UmUserAccount mobileUser = userAccountService.login(mobile, true);
//                if (openUser == null)
//                    return new RestResponseForJson<>(RestResponseStatus.BAD_REQUEST, null, "openId参数错误！");
//                if (mobileUser != null)
//                    return new RestResponseForJson<>(RestResponseStatus.BAD_REQUEST, null, "手机号已被别人绑定！");
//                if (mobileUser == null) {//手机未注册
//                    openUser.setUserName(mobile);
//                    openUser.setPhone(mobile);
//                    openUser.setAccountName(mobile);
//                    String password = "";
//                    while (password.length() != 6) {
//                        password = (int) Math.ceil(Math.random() * 1000000) + "";
//                    }
//                    password = "123456";
//                    //用户密码
//                    openUser.setPassword(PasswordHelper.encryptPassword(mobile, password));
//                    user = userAccountService.update(openUser);
//                    //发送短信
//                    /*Map<String,String> map =new HashMap<String, String>();
//                    map.put("mob",mobile);
//                    map.put("password", password);
//                    //发送短信
//                    try {
//                        boolean success = SMSSendApiUtil.sendsms(mobile,password);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }*/
//                }
//                return new RestResponseForJson<>(RestResponseStatus.OK, login(user), "登陆成功");
//            } else {
//                return new RestResponseForJson<>(RestResponseStatus.BAD_REQUEST, null, "验证码错误");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new RestResponseForJson<>(RestResponseStatus.BAD_REQUEST, null, "登陆失败");
//        }
//    }
//
//
//    /**
//     * 设置返回的tonken信息
//     *
//     * @param user
//     * @return
//     */
    private Map<String, Object> loginInfo(UserAccountDto user) throws Exception {
        String toJson = Converter.toJson(user);
        Map result = Converter.parseObject(toJson, Map.class);
        String accessToken = "TOKEN_" + user.getId() + "/" + UUID.randomUUID().toString();
        result.put("token", accessToken);

        Integer id = (Integer) result.get("id");
        result.put("accid", id);
        result.remove("id");
        //用户token存入Redis中
        redisUtil.set(user.getId().toString(), accessToken, EXPIRE_TIME);
        redisUtil.set(accessToken, Long.valueOf(user.getId()), EXPIRE_TIME);

        return result;
    }


}