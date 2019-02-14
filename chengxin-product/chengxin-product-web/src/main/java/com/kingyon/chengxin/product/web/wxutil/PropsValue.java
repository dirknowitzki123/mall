package com.kingyon.chengxin.product.web.wxutil;

import com.kingyon.chengxin.framework.config.AppConfig;

/**
 * <p>Title:PropsValue</p>
 * <p>Description:系统配置项 values</p>
 * <p>Company:成都金翼致远科技有限公司</p>
 *
 * @author Jimmy
 * @date 2016-3-16 下午6:08:08
 */
public class PropsValue {

     /**
     * redis token过期时间,默认7200
     */
//    public static final Long TOKEN_LIFECYCLE = Props.getLong(PropsKey.TOKEN_LIFECYCLE, 7200L);

    public static final String PREFIX = "common.weixin.";

    /**
     * 微信支付配置
     */
    public static final String WECHAT_KEY = AppConfig.getProperty(PREFIX+"key");
    public static final String WECHAT_APP_SECRET =  AppConfig.getProperty(PREFIX+"appSecre");
    public static final String WECHAT_APPID = AppConfig.getProperty(PREFIX+"appid");
    public static final String WECHAT_MCHID = AppConfig.getProperty(PREFIX+"mchid");
    public static final String WECHAT_CERT_LOCALPATH = AppConfig.getProperty(PREFIX+"certlocalpath");
    public static final String WECHAT_CERT_PASSWORD = AppConfig.getProperty(PREFIX+"certpassword");

    public static final String WX_PAY_NOTIFY_URL = AppConfig.getProperty(PREFIX+"notifyUrl");
    public static final String WX_PAY_ADDRESS_IP = AppConfig.getProperty(PREFIX+"addressIp");

    //微信支付API
    public static final  String PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";


}
