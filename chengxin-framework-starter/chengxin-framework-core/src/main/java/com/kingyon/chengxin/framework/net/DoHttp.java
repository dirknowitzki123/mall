package com.kingyon.chengxin.framework.net;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.security.action.GetPropertyAction;

import java.security.AccessController;
import java.util.Map;

/**
 *
 * 类功能说明：httpclient工具类,基于httpclient 4.x
 * Title: DoHttp.java
 * @version V1.0
 */
@Slf4j
@Deprecated
public class DoHttp {
    public static String dfltEncName = (String) AccessController.doPrivileged(new GetPropertyAction("file.encoding"));

    /**
     * 获得RestTemplate
     * @return
     */
    public static RestTemplate getClient() {
        return RestClient.getClient();
    }

    /**
     * GET  通过Url获得字符串
     * @param url
     * @return  字符串
     */
    public static String doGet(String url) {
        return doGet(url, String.class, null);
    }

    /**
     * GET  带参数组装请求
     * @param url
     * @param urlParams
     * @return  字符串
     */
    public static String doGet(String url, Map<String, String> urlParams) {
        return doGet(url, String.class, urlParams);
    }

    /**
     * GET请求，返回指定DTO对象
     * @param url
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> T doGet(String url, Class<T> responseDto) {
        return doGet(url, responseDto, null);
    }

    /**
     * GET请求，返回指定DTO对象，并可指定参数
     * @param url
     * @param responseDto
     * @param urlParams
     * @param <T>
     * @return
     */
    public static <T> T doGet(String url, Class<T> responseDto, Map<String, String> urlParams) {
        try {
            if (MapUtils.isEmpty(urlParams))
                return RestClient.getClient().getForObject(url, responseDto);

            return RestClient.getClient().getForObject(url, responseDto, urlParams);
        } catch (Exception e) {
            log.error("GET请求出错：{}", url, e);
        }

        return null;
    }

    /**
     * POST方法。指定返回对象
     * @param url
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Class<T> responseDto) {
        return doPost(url, null, null, responseDto);
    }

    /**
     * POST方法。指定“form表单”对象（Map）。并指定返回对象
      * @param url
     * @param body
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Map<String, String> body, Class<T> responseDto) {
        return doPost(url, null, prepareParam(body), responseDto);
    }

    /**
     * POST方法。指定body对象。并指定返回对象
     * @param url
     * @param body
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, Object body, Class<T> responseDto) {
        return doPost(url, null, body, responseDto);
    }

    /**
     * POST方法。可指定头信息，指定“form表单”对象（Map）。并指定返回对象
     * @param url
     * @param headers
     * @param body
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, HttpHeaders headers, Map<String, String> body, Class<T> responseDto) {
        return doPost(url, headers, prepareParam(body), responseDto);
    }

    /**
     * POST方法。可指定头信息，指定body对象。并指定返回对象
     * @param url
     * @param headers   头信息定义：HttpHeaders
     * @param body      body内容：可以是字符串、byte[]、JSON数据、DTO等
     * @param responseDto   返回的DTO对象，或其他对象
     * @param <T>
     * @return
     */
    public static <T> T doPost(String url, HttpHeaders headers, Object body, Class<T> responseDto) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers);

        try {
            ResponseEntity<T> resp = RestClient.getClient().postForEntity(url, httpEntity, responseDto);

            return resp.getBody();
        } catch (Exception e) {
            log.error("POST请求出错：{}", url, e);
        }

        return null;
    }

    /**
     * Map指定的参数，转换为url参数或form参数编码
     * @param paramMap
     * @return
     */
    public static String prepareParam(Map<String, String> paramMap) {
        if (paramMap == null || paramMap.isEmpty())
            return "";

        else {
            StringBuilder sb = new StringBuilder();
            for (String key : paramMap.keySet()) {
                String value = paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=");
                    try {
                        sb.append(java.net.URLEncoder.encode(value, "UTF-8"));
                    }catch(Exception e){
                        try {
                            sb.append(java.net.URLEncoder.encode(value, dfltEncName));
                        }catch(Exception e1){}
                    }
                }
            }
            return sb.toString();
        }
    }

    public void doDelete(String url) {
        getClient().delete(url);
    }

    public void doDelete(String url, Object... urlVariables) {
        getClient().delete(url, urlVariables);
    }

    public void doDelete(String url, Map<String, String> paramMap) {
        getClient().delete(url, paramMap);
    }

    public void doPut(String url, Object body) {
        getClient().put(url, body);
    }

    public void doPut(String url, Object body, Object... urlVariables) {
        getClient().put(url, body, urlVariables);
    }

    public void doPut(String url, Object body, Map<String, String> paramMap) {
        getClient().put(url, body, paramMap);
    }

    @Data
    static class RespDto
    {
        String code;
        String message;
        Object data;

    }
    @Data
    static class RequestDto
    {
        String code;
        String message;
        Object data;

    }
    public static void main(String[] args) {

//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("test--------1", "dddddddddddddddddddddd");
//        headers.add("test--------2", "eeeeeeeeeeeeeeeee");
//        RespDto resp = doPost("http://localhost:8080/v1/user/http", headers, "test-----------------test----", RespDto.class);
//        log.info("", resp.toString());
//
//        //第二项测试
//        headers = new HttpHeaders();
//        headers.setContentType(type);
//        resp = doPost("http://localhost:8080/v1/user/http", headers, "test-----------------test----", RespDto.class);


        RequestDto request = new RequestDto();
        request.code = "1";

        HttpRestClient restClient = HttpRestClient.getHttpClient("test");
//        restClient.addHeader("ContentType", "application/json; charset=UTF-8");
//        restClient.addHeader("test--------1", "dddddddddddddddddddddd");
//        restClient.addHeader("test--------2", "eeeeeeeeeeeeeeeee");
//        RespDto resp = restClient.doPost("http://192.168.8.50:8090/api/epay/finUserPay/v1/getOid", "test-----------------test----", RespDto.class);

        String resp = restClient.doPost("https://api.mch.weixin.qq.com/pay/orderquery", String.class);

//        Map<String, String> params = new HashedMap();
//        params.put("code", "1");
//
//        Response<RespDto> resp = restClient.doPost("http://localhost:8080/v1/user/http", params, new TypeReference<Response<RespDto>>(){} );
//        log.info("----", resp.toString());
//        resp = restClient.doPost("http://localhost:8080/v1/user/http", params, Response.class, RespDto.class );
        log.info("----", resp.toString());
    }
}