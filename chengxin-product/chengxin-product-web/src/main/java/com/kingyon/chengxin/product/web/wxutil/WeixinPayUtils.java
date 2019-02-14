package com.kingyon.chengxin.product.web.wxutil;

import com.kingyon.chengxin.framework.exception.WebException;
import com.kingyon.chengxin.product.ProductErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.kingyon.chengxin.product.web.wxutil.WechatSendUtil.okHttpRest;

/**
 * 微信支付工具类
 *
 * @author Sam
 */
@Slf4j
public class WeixinPayUtils {


    /**
     * 微信支付下单
     *
     * @param orderNumber 订单号
     * @param body        说明
     * @param totalFee    价格，单位分
     * @return
     */
    public static Map<String, String> placeOrder(String orderNumber, String body, int totalFee, String openId, String tradeType) throws Exception {

        Map<String, String> result = new HashMap<>();
        String requsetXml = "";
        try {
            requsetXml = prepareWXData(orderNumber, body, totalFee, openId, tradeType);
            Map<String, String> xmlString2Map = XMLUtil.xmlString2Map(requsetXml);
            log.info("reqXml----------------> : " + requsetXml);
            String responseXml = okHttpRest.doPost(PropsValue.PAY_API, requsetXml, String.class);
            log.info("resXml----------------> : " + responseXml);
            Map<String, String> responseMap = XMLUtil.xmlString2Map(responseXml);
            if ("fail".equalsIgnoreCase(responseMap.get("return_code"))) {
                throw new WebException(ProductErrorCode.THIRD_ERROR, responseMap.get("return_msg"));
            } else if ("fail".equalsIgnoreCase(responseMap.get("return_code"))) {
                throw new WebException(ProductErrorCode.THIRD_ERROR, responseMap.get("err_code_des"));
            }
            String timestamp = String.valueOf(System.currentTimeMillis());
//            String noncestr = xmlString2Map.get("nonce_str");
            String noncestr = UUID.randomUUID().toString().replace("-", "");
            //微信返回参数二次加签
            result.put("appId", responseMap.get("appid"));
            result.put("timeStamp", timestamp);
            result.put("nonceStr", noncestr);
            result.put("package", "prepay_id=" + responseMap.get("prepay_id"));
            result.put("signType", "MD5");
            String sign = PayCommonUtil.createSign("UTF-8", result, PropsValue.WECHAT_KEY);
            result.put("paySign", sign);
            log.info("微信二次签名结果:{}", result.toString());
        } catch (WebException e) {
            throw e;
        }
        return result;
    }


    public static String prepareWXData(String orderNumber, String body, int totalFee, String deviceInfo, String tradeType) throws Exception {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> signMap = new HashMap<>();

        signMap.put("appid", PropsValue.WECHAT_APPID);//APP_ID
        signMap.put("mch_id", PropsValue.WECHAT_MCHID);//商户ID
        signMap.put("nonce_str", uuid);
        signMap.put("body", body);
        signMap.put("out_trade_no", orderNumber);//统一支付单ID
        signMap.put("total_fee", String.valueOf(totalFee));//交易金额
        signMap.put("spbill_create_ip", PropsValue.WX_PAY_ADDRESS_IP); //

        signMap.put("notify_url", PropsValue.WX_PAY_NOTIFY_URL);//支付成功后回调地址，改订单状态
        signMap.put("trade_type", tradeType);
        /*
         * 1如果数据库存的有openid就直接取数据库的，
         * 2通过getOpenIdByCode（）方法获取
         */
        signMap.put("openid", deviceInfo);//用户对应小程序的openid,和公众号的openid不一样
        String sign = PayCommonUtil.createSign("UTF-8", signMap, PropsValue.WECHAT_KEY); //签名
        signMap.put("sign", sign);
        String requsetXml = XMLUtil.map2Xmlstring(signMap);

        return requsetXml;
    }



}
