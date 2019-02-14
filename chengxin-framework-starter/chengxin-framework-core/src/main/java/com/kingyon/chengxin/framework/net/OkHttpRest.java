package com.kingyon.chengxin.framework.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kingyon.chengxin.framework.SysErrorCode;
import com.kingyon.chengxin.framework.exception.SysException;
import com.kingyon.chengxin.framework.util.Converter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.File;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @title：使用spring的restTemplate替代httpclient工具
 *   提供配置连接池管理。可以针对相同的配置建立配置池。
 *
 */
@Component
@Slf4j
public class OkHttpRest {
    public static final String DEFUAlT = "default-ok3httpclient";

    String poolName = "default-ok3httpclient";
    OkHttpClient.Builder builder;
    PoolingHttpClientConnectionManager pollingConnectionManager;
    OkHttp3ClientHttpRequestFactory clientHttpRequestFactory;

    HttpHeader headers = new HttpHeader();

    List<HttpMessageConverter<?>> messageConverters;

    private RestTemplate restTemplate;

    /**管理多个连接池*/
    private static Map<String, OkHttpRest> clients = new ConcurrentHashMap<>();

    /**
     * 获得默认配置池
     * @return
     */
    public static OkHttpRest getHttpClient() {
        return getHttpClient(OkHttpRest.DEFUAlT);
    }

    /**
     * 获得指定的“poolName”的配置连接池
     * @param poolName
     * @return
     */
    public static OkHttpRest getHttpClient(String poolName) {
        if (poolName == null)
            poolName = OkHttpRest.DEFUAlT;

        OkHttpRest client = clients.get(poolName);
        if (client == null) {
            client = new OkHttpRest(poolName);
            clients.put(poolName, client);
        }
        return client;
    }

    OkHttpRest() {
        this(DEFUAlT);
    }

    OkHttpRest(String poolName) {
        this.poolName = poolName;

//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//
//        SSLContext sslContext = null;
//        try {
//            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                public boolean isTrusted(X509Certificate[] arg0, String arg1) {
//                    return true;
//                }
//            }).build();
//        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//        httpClientBuilder.setSSLContext(sslContext);
//
//        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
//
//        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
//        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", sslSocketFactory)
//                .build();
//
//        // 长连接保持30秒
//        pollingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//        // 总连接数
//        pollingConnectionManager.setMaxTotal(1000);
//        // 同路由的并发数
//        pollingConnectionManager.setDefaultMaxPerRoute(1000);
//        httpClientBuilder.setConnectionManager(pollingConnectionManager);
//        // 重试次数，默认是3次，没有开启
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
//        // 保持长连接配置，需要在头添加Keep-Alive
//        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
//
//        defaultHeaders = new ArrayList<>();
//        defaultHeaders.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
////        defaultHeaders.add(new BasicHeader("Accept-Charset", "big5, big5-hkscs, cesu-8, euc-jp, euc-kr, gb18030, gb2312, gbk,iso-8859-1,utf-16, utf-16be, utf-16le, utf-32, utf-32be, utf-32le, utf-8"));
////        defaultHeaders.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
////        defaultHeaders.add(new BasicHeader("Accept-Language", "zh-CN"));
//        defaultHeaders.add(new BasicHeader("Connection", "close"));
//        httpClientBuilder.setDefaultHeaders(defaultHeaders);
//
//        CloseableHttpClient httpClient = httpClientBuilder.build();

        // Create a trust manager that does not validate certificate chains
        final X509TrustManager trustAllCerts = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustAllCerts}, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
        }catch (Exception e) {
            log.error("", e);
        }
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        builder = new OkHttpClient.Builder().connectionPool(new ConnectionPool(60, 5L, TimeUnit.MINUTES))
                .sslSocketFactory(sslSocketFactory, trustAllCerts)
                .hostnameVerifier(hostnameVerifier);

        // httpClient连接配置，底层是配置RequestConfig
        clientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(builder.build() );

        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(1000 * 10);
        // 数据读取超时时间，即SocketTimeout

        clientHttpRequestFactory.setReadTimeout(1000 * 30);

        // 添加内容转换器
        messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());

        /**XML转换器*/
        final XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
            private static final long serialVersionUID = 1L;
            // 反序列化时调用
            @Override
            public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                return method.getName().substring(3);
            }
            // 序列化时调用
            @Override
            public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                return method.getName().substring(3);
            }
        });
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter(xmlMapper) );
        /**Json转换器*/
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
//        restTemplate.set
        log.info("OkHttp3RestClient初始化完成");
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    // 总连接数
    public void setMaxTotal(int val) {
        pollingConnectionManager.setMaxTotal(val);
    }

    // 同路由的并发数
    public void setDefaultMaxPerRoute(int val) {
        pollingConnectionManager.setDefaultMaxPerRoute(val);
    }

    // 连接超时
    public void setConnectTimeout(int val) {
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(val);
    }

    // 数据读取超时时间，即SocketTimeout
    public void setReadTimeout(int val) {
        clientHttpRequestFactory.setReadTimeout(val);
    }


    public HttpHeader getHeaders() {
        return headers;
    }
    /**
     * 添加head。如key已存在，则替换。
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        headers.addHeader(key, value );
//        List<String> head = headers.get(key);
//
//        if (head == null ) {
//            headers.add( key, value );
//        } else {
//            head.clear();
//            head.add(value);
//        }
    }

    /**
     * 添加转换器
     * @param converter
     */
    public void addMessageConverter(HttpMessageConverter converter) {
        if (messageConverters.contains(converter ))
            return;
        messageConverters.add(converter);
    }

    /**
     * GET  通过Url获得字符串
     * @param url
     * @return  字符串
     */
    public String doGet(String url) {
        return doGet(url, null, String.class);
    }

    /**
     * GET  带参数组装请求
     * @param url
     * @param urlParams
     * @return  字符串
     */
    public String doGet(String url, Map<String, Object> urlParams) {
        return doGet(url, urlParams, String.class);
    }

    /**
     * GET请求，返回指定DTO对象
     * @param url
     * @param responseDto
     * @param <T>
     * @return
     */
    public <T> T doGet(String url, Class<T> responseDto) {
        return doGet(url, null, responseDto);
    }

    public <T> T doGet(String url, Class<T> responseDto, Class<?>... fieldClass) {
        return doGet(url, null, responseDto, fieldClass);
    }

    public <T> T doGet(String url, TypeReference<T> responseDto) {
        return doGet(url, null, responseDto);
    }

    /**
     * GET请求，返回指定DTO对象，并可指定参数
     * @param url
     * @param responseDto
     * @param urlParams
     * @param <T>
     * @return
     */
    public <T> T doGet(String url, Map<String, Object> urlParams, Class<T> responseDto) {
//        HttpEntity<Object> httpEntity = new HttpEntity<Object>("", this.headers.getHeaders());
//        try {
////            if (MapUtils.isEmpty(urlParams))
////                return this.restTemplate.getForObject(url, responseDto);
//            url = prepareParam(url, urlParams);
//            log.info("-----------doGet:" + url);
//            return this.restTemplate.getForObject(url , responseDto);
//        } catch (Exception e) {
//            log.error("GET请求出错：{}", url, e);
//        }

        return doGet(url, this.headers, urlParams, responseDto);
    }

    public <T> T doGet(String url, HttpHeader header, Map<String, Object> urlParams, Class<T> responseDto) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>("", header.getHeaders());
        try {
//            if (MapUtils.isEmpty(urlParams))
//                return this.restTemplate.getForObject(url, responseDto);
            url = prepareParam(url, urlParams);
            log.info("-----------doGet:" + url);
            return this.restTemplate.getForObject(url , responseDto);
        } catch (Exception e) {
            log.error("GET请求出错：{}", url, e);
            throw new SysException(SysErrorCode.FAIL, "doGet err：" + e.getMessage());
        }

//        return null;
    }

    /**
     * GET请求，返回指定DTO对象(含多级或泛型属性对象)，并可指定参数
     * @param url
     * @param clazz
     * @param urlParams
     * @param <T>
     * @return
     */
    public <T> T doGet(String url, Map<String, Object> urlParams, TypeReference<T> clazz) {
        try {
//            if (MapUtils.isEmpty(urlParams))
//                return this.restTemplate.getForObject(url, responseDto);
            url = prepareParam(url, urlParams);
//            log.info("-----------doGet:" + url);
            String json = this.restTemplate.getForObject(url , String.class);
            return (T) Converter.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("GET请求出错：{}", url, e);
            throw new SysException(SysErrorCode.FAIL, "doGet err：" + e.getMessage());
        }

//        return null;
    }

    /**
     * GET请求，返回指定DTO对象(含多级或泛型属性对象)，并可指定参数
     * @param url
     * @param clazz
     * @param urlParams
     * @param <T>
     * @return
     */
    public <T> T doGet(String url, Map<String, Object> urlParams, Class<?> clazz, Class<?>... fieldClazz) {
        try {
//            if (MapUtils.isEmpty(urlParams))
//                return this.restTemplate.getForObject(url, responseDto);
            url = prepareParam(url, urlParams);
//            log.info("-----------doGet:" + url);
            String json = this.restTemplate.getForObject(url , String.class);
            return Converter.parseObject(json, clazz, fieldClazz);
        } catch (Exception e) {
            log.error("GET请求出错：{}", url, e);
            throw new SysException(SysErrorCode.FAIL, "doGet err：" + e.getMessage());
        }

//        return null;
    }

    /**
     * POST方法。指定返回对象
     * @param url
     * @param responseDto
     * @param <T>
     * @return
     */
    public <T> T doPost(String url, Class<T> responseDto) {
        return doPost(url,  null, responseDto);
    }

    public <T> T doPost(String url, Class<?> responseDto, Class<?>... fieldClass) {
        return doPost(url,  null, responseDto, fieldClass);
    }

    public <T> T doPost(String url, TypeReference<T> responseDto) {
        return doPost(url,  null, responseDto);
    }

    /**
     * POST方法。可指定头信息，指定“form表单”对象（Map）。并指定返回对象
     * @param url
     * @param body
     * @param responseDto
     * @param <T>
     * @return
     */
    public <T> T doPost(String url, Map<String, Object> body, Class<T> responseDto) {
        return doPost(url, prepareParam(body), responseDto);
    }

//    public <T> T doPost(String url, Map<String, List<String>> body, Class<T> responseDto) {
//        return doPost(url, prepareParamList(body), responseDto);
//    }

    public <T> T doPost(String url, Map<String, Object> body, Class<?> responseDto, Class<?>... fieldClass) {
        return doPost(url,  prepareParam(body), responseDto, fieldClass);
    }

    public <T> T doPost(String url, Map<String, Object> body, TypeReference<T> responseDto) {
        return doPost(url,  body, responseDto);
    }

    /**
     * POST方法。可指定头信息，指定body对象。并指定返回对象
     * @param url
     * @param body      body内容：可以是字符串、byte[]、JSON数据、DTO等
     * @param responseDto   返回的DTO对象，或其他对象
     * @param <T>
     * @return
     */
    public <T> T doPost(String url, Object body, Class<T> responseDto) {
        return doPost(url, this.headers, body, responseDto);
    }

    public <T> T doPost(String url, HttpHeader header, Object body, Class<T> responseDto) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, header.getHeaders());

        try {
            ResponseEntity<T> resp = this.restTemplate.postForEntity(url, httpEntity, responseDto);

            return resp.getBody();
        } catch (Exception e) {
            log.error("POST请求出错：{}", url, e);
            throw new SysException(SysErrorCode.FAIL, "doPost err：" + e.getMessage());
        }
    }

//    /**
//     * POST方法。可指定头信息，指定body对象。并指定返回对象(含多层的泛型或字段对象)
//     * @param url
//     * @param body      body内容：可以是字符串、byte[]、JSON数据、DTO等
//     * @param clazz     TypeReference<T> 返回的DTO对象，或其他对象
//     * @param <T>
//     * @return
//     */
//    public <T> T doPost(String url, Object body, TypeReference<T> clazz) {
//        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, this.headers);
//
//        try {
//            ResponseEntity<String> resp = this.restTemplate.postForEntity(url, httpEntity, String.class);
//            String json = resp.getBody();
//
//            return Converter.parseObject(json, clazz);
//        } catch (Exception e) {
//            log.error("POST请求出错：{}", url);
//            throw new RuntimeException("doPost err：" + e.getMessage(), e);
//        }
//    }

    /**
     * POST方法。可指定头信息，指定body对象。并指定返回对象(含多层的泛型或字段对象)
     * @param url
     * @param body      body内容：可以是字符串、byte[]、JSON数据、DTO等
     * @param responseDto     返回的DTO对象，或其他对象
     * @param fieldClass      返回DTO中的属性对象
     * @param <T>
     * @return
     */
    public <T> T doPost(String url, Object body, Class<?> responseDto, Class<?>... fieldClass) {
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers.getHeaders());

        try {
            ResponseEntity<String> resp = this.restTemplate.postForEntity(url, httpEntity, String.class);
            String json = resp.getBody();

            return Converter.parseObject(json, responseDto, fieldClass);
        } catch (Exception e) {
            log.error("POST请求出错：{}", url, e);
            throw new SysException(SysErrorCode.FAIL, "doPost err：" + e.getMessage());
        }
    }

    public static String prepareParam(String url, Map<String, Object> paramMap) {
        String param = prepareParam(paramMap);
        if (url.indexOf("?", 7) > 7)
            return url + "&" + param;
        else
            return url + "?" + param;
    }
    /**
     * Map指定的参数，转换为url参数或form参数编码
     * @param paramMap
     * @return
     */
    public static String prepareParam( Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty())
            return "";
        else {
            StringBuilder sb = new StringBuilder();
            for (String key : paramMap.keySet()) {
                Object objs = paramMap.get(key);
                if (objs instanceof String[] ) {
                    for (String value : (String[])objs)
                        sb = toParams(sb, key, value);
                } else if (objs instanceof List) {
                    List list = (List)objs;
                    for (int i = list.size() - 1; i >= 0; i--)
                        sb = toParams(sb, key, String.valueOf(list.get(i) ) );
                } else {
                    sb = toParams(sb, key, String.valueOf(objs ) );
                }

            }

            return sb.toString();
        }
    }

    private static StringBuilder toParams(StringBuilder sb, String key, String value) {
        if (sb.length() < 1) {
            sb.append(key).append("=").append(value);
        } else {
            sb.append("&").append(key).append("=");
            try {
                sb.append(java.net.URLEncoder.encode(value, "UTF-8"));
            } catch (Exception e) {
                try {
                    sb.append(java.net.URLEncoder.encode(value, DoHttp.dfltEncName));
                } catch (Exception e1) {
                }
            }
        }
        return sb;
    }

//    public static String prepareParam(String url, Map<String, String> paramMap) {
//        String param = prepareParam(paramMap);
//        if (url.indexOf("?", 7) > 7)
//            return url + "&" + param;
//        else
//            return url + "?" + param;
//    }
//    /**
//     * Map指定的参数，转换为url参数或form参数编码
//     * @param paramMap
//     * @return
//     */
//    public static String prepareParam( Map<String, String> paramMap) {
//        if (paramMap == null || paramMap.isEmpty())
//            return "";
//
//        else {
//            StringBuilder sb = new StringBuilder();
//            for (String key : paramMap.keySet()) {
//                String value = paramMap.get(key);
//                if (sb.length() < 1) {
//                    sb.append(key).append("=").append(value);
//                } else {
//                    sb.append("&").append(key).append("=");
//                    try {
//                        sb.append(java.net.URLEncoder.encode(value, "UTF-8"));
//                    } catch (Exception e) {
//                        try {
//                            sb.append(java.net.URLEncoder.encode(value, DoHttp.dfltEncName));
//                        } catch (Exception e1) {
//                        }
//                    }                }
//            }
//
//            return sb.toString();
//        }
//    }

    public void doDelete(String url) {
        this.restTemplate.delete(url);
    }

    public void doDelete(String url, Object... urlVariables) {
        this.restTemplate.delete(url, urlVariables);
    }

    public void doDelete(String url, Map<String, String> paramMap) {
        this.restTemplate.delete(url, paramMap);
    }

    public void doPut(String url, Object body) {
        this.restTemplate.put(url, body);
    }

    public void doPut(String url, Object body, Object... urlVariables) {
        this.restTemplate.put(url, body, urlVariables);
    }

    public void doPut(String url, Object body, Map<String, String> paramMap) {
        this.restTemplate.put(url, body, paramMap);
    }

    public static void main(String[] args) {
        String path = "E:\\QQMusic\\新建文件夹";

        File dir = new File(path);
        File[] files = dir.listFiles();
        for(File f : files) {
            if (f.isFile() ) {
                String oldName = f.getName();
                int idx = oldName.indexOf("-");
                if (idx > 0) {
                    String first = oldName.substring(0, idx);
                    String second = oldName.substring(idx + 1);
                    f.renameTo(new File(path + "\\" + first.trim() + "-" + second.trim()));
                }
            }
        }
    }
}