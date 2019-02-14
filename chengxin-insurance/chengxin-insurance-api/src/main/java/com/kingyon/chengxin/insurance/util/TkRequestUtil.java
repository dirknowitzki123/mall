package com.kingyon.chengxin.insurance.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kingyon.chengxin.framework.config.AppConfig;
import com.kingyon.chengxin.framework.exception.ServiceException;
import com.kingyon.chengxin.insurance.InsuranceErrorCode;
import com.kingyon.chengxin.insurance.dto.tkdto.PayUrlDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Aspen
 * @Created: 2018/11/20 0020.
 */
@Slf4j
public class TkRequestUtil {


    public static String AES_KEY = AppConfig.getProperty("common.taikang.aesKey");
    public static String MD5_KEY = AppConfig.getProperty("common.taikang.md5Key");
    public static String URL = AppConfig.getProperty("common.taikang.url");
    public static String CHANNEL_ID = AppConfig.getProperty("common.taikang.channelId");
    public static String NOTIFY_URL = AppConfig.getProperty("common.taikang.notifyUrl");

    public static String requestTk(String content, String apiName, String requestNo) {
        //密文加密AES,并Base64编码
        String biz_content = encryptAES(content, AES_KEY);
        Map message = new HashMap();
        message.put("channel_id", CHANNEL_ID);
        message.put("biz_content", biz_content);
        message.put("request_no", StringUtils.isEmpty(requestNo) ? CodeGenerateUtils.generateUnionPaySn() : requestNo);
        message.put("timestamp", System.currentTimeMillis());
        String sign = JSONObject.toJSONString(message, SerializerFeature.MapSortField);
        //进行md5运算
        sign = md5(sign + MD5_KEY);
        message.put("sign", sign);
        //使用httpclient调用端口
        String resReport = initHttpClient(URL + apiName, message);
        JSONObject jsonObject = JSON.parseObject(resReport);
        String result = jsonObject.getString("result_code");
        //校验返回结果
        String response = "";
        if ("SUCCESS".equals(result)) {
            String result_biz_content = jsonObject.getString("biz_content");
            //校验验签是否正确
            boolean check = checkSign(jsonObject, MD5_KEY);
            if (check) {
                //对返回业务参数解密
                response = decryptAES(result_biz_content, AES_KEY);
                log.info("请求泰康响应数据 -apiName-{} : {}", apiName, response);
            } else {
                throw new ServiceException(InsuranceErrorCode.THIRD_ERROR, "验签校验不成功");
            }
        } else {
            log.error("泰康请求数据  未加密:{},加密 :{}:", content, message);
            response = jsonObject.getString("err_msg");
            throw new ServiceException(InsuranceErrorCode.THIRD_ERROR, response);
        }
        return response;
    }


    public static String getPayUrl(PayUrlDto dto, String totalFee, String proName) {
        Map message = new HashMap();
        message.put("channel_id", CHANNEL_ID);
        message.put("out_trade_no", dto.getOrderNumber());
        message.put("total_fee", totalFee);
        message.put("productname", proName);
        message.put("notifyurl", NOTIFY_URL);
        message.put("payType", dto.getPayType());
        message.put("callbackurl", dto.getCallbackurl());
        message.put("failurl", dto.getFailurl());

        String sign_code = "total_fee=" + totalFee + "&key=" + MD5_KEY;
        String sign = md5(sign_code);
        message.put("sign", sign);
        log.info("泰康请求加密数据:{}", message);
        String resReport = initHttpClient(URL + "pay", message);
        log.info("泰康支付请求结果 :{}", resReport);
        return resReport;
    }

    ;


    /**
     * 校验返回验签
     *
     * @param obj     请求返回的数据
     * @param signKey 验签加密密钥
     * @return
     */
    public static boolean checkSign(JSONObject obj, String signKey) {
        Map resultMap = new HashMap();
        resultMap.put("timestamp", obj.getLongValue("timestamp"));
        resultMap.put("biz_content", obj.getString("biz_content"));
        resultMap.put("result_code", obj.getString("result_code"));
        String resultJson = JSONObject.toJSONString(resultMap, SerializerFeature.MapSortField);
        String sign = obj.getString("sign");
        String signGen = md5(resultJson + signKey);
        System.out.println(signGen);
        if (sign.equals(signGen)) {
            return true;
        }
        return false;
    }

    /**
     * 初始化httpclient，并调用接口，返回接口数据
     *
     * @param url
     * @param params
     * @return
     */
    public static String initHttpClient(String url, Map params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type", "application/json;charset=utf-8");
        post.setHeader("Accept", "application/json;charset=utf-8");
        HttpEntity entity = new StringEntity(JSONObject.toJSONString(params), Charset.forName("UTF-8"));
        post.setEntity(entity);
        HttpResponse response;
        HttpEntity resEntity;
        try {
            response = client.execute(post);
            resEntity = response.getEntity();
            return EntityUtils.toString(resEntity); //响应信息
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close(); //关闭连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * AES 加密
     *
     * @param content
     * @param key
     * @return
     */
    public static String encryptAES(String content, String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //需手动指定 SecureRandom 随机数生成规则，否则在Linux上可能生成随机key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content
     * @param key
     * @return
     */
    public static String decryptAES(String content, String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(Base64.decodeBase64(content));
            return new String(encrypted, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5运算
     *
     * @param content
     * @return
     */
    public static String md5(String content) {
        try {
            return Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(content.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
