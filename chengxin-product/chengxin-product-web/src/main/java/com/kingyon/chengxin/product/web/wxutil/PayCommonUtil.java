package com.kingyon.chengxin.product.web.wxutil;

import com.kingyon.chengxin.framework.util.MD5Util;
import org.apache.http.Consts;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;


public class PayCommonUtil {

    private HttpServletRequest request;

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, Map<String, String> packageParams, String apiKey) {
        //算出摘要
        String sign = createSign(characterEncoding, packageParams, apiKey);
        String tenpaySign = (packageParams.get("sign")).toUpperCase();
        return tenpaySign.equals(sign);
    }

    /**
     * @param characterEncoding 编码格式
     * @return
     * @author
     * @date 2016-4-22
     * @Description：sign签名
     */
    public static String createSign(String characterEncoding, Map<String, String> packageParams, String appKey) {
        // 利用TreeMap对参数进行排序(字典序)
        TreeMap<String, String> sortedParams = new TreeMap<String, String>();
        sortedParams.putAll(packageParams);

        StringBuffer stringTemp = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = sortedParams.entrySet().iterator();
        boolean first = true;
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            String key = e.getKey();
            String val = e.getValue();

            // sign字段不参与签名, 值为空的字段不参与签名
            // 其余字段,用key1=val1&key2=val2格式拼接起来
            if (!key.equals("sign") && !StringUtils.isEmpty(val)) {
                stringTemp.append(key + "=" + val + "&");
            }
        }
        if (appKey != null) {
            stringTemp.append("key=" + appKey);
        }
        String sign = MD5Util.MD5Encode(stringTemp.toString(), characterEncoding).toUpperCase();

        return sign;
    }
    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }
    /**
     * @param parameters 请求参数
     * @return
     * @author
     * @date 2016-4-22
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    /**
     * 解析请求
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseRequest(HttpServletRequest request) throws Exception {

        Map<String, String> packageParams = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            packageParams.put(name, valueStr);
        }
        return packageParams;
    }

    /**
     * 读取请求信息
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String readRequestMsg(HttpServletRequest request) throws IOException {
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            String s;
            in = new BufferedReader(new InputStreamReader(request.getInputStream(), Consts.UTF_8));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }

    /**
     * 获取UUID，去除中间横线
     */
    public static String getUUIDRemoveLine() {
        return getUUID().replaceAll("-", "");
    }


    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


    public static String wxNotifyReturn(String returnCode, String returnMsg) {
        String retMsg = "<xml>"
                + "<return_code><![CDATA[" + returnCode + "]]></return_code>"
                + "<return_msg><![CDATA[" + returnMsg + "]]></return_msg>"
                + "</xml>";
        return retMsg;

    };

//    /**
//     *  支付宝验证签名
//     * @param params
//     * @param aliPubKey
//     * @param charset
//     * @param signType
//     * @return
//     * @throws Exception
//     */
//    public static boolean checkAlipaySignature(Map<String, String> params,String aliPubKey, String charset, String signType) throws Exception {
//        try {
//            return AlipaySignature.rsaCheckV1(params, aliPubKey, charset, signType);
//        } catch (Exception e) {e.printStackTrace();}
//        return false;
//    }
//



}  
