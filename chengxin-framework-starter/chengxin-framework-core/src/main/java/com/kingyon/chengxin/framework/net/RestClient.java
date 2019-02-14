package com.kingyon.chengxin.framework.net;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title：使用spring的restTemplate替代httpclient工具
 */
@Component
@Slf4j
@Deprecated
public class RestClient {

    private static Map<String, HttpRestClient> clients = new ConcurrentHashMap<>();

//    private static RestTemplate restTemplate;
//
//    static {
//    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//
//    	SSLContext sslContext = null;
//		try {
//			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//			    public boolean isTrusted(X509Certificate[] arg0, String arg1) {
//			        return true;
//			    }
//			}).build();
//		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
//			e.printStackTrace();
//		}
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
//        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//
////        HttpClientConnectionManager httpClientConnectionManager = new BasicHttpClientConnectionManager(socketFactoryRegistry);
//        // 总连接数
//        pollingConnectionManager.setMaxTotal(1000);
//        // 同路由的并发数
//        pollingConnectionManager.setDefaultMaxPerRoute(1000);
//
//        httpClientBuilder.setConnectionManager(pollingConnectionManager);
//
//        // 重试次数，默认是3次，没有开启
//        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
//        // 保持长连接配置，需要在头添加Keep-Alive
//
//        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
//
//        List<Header> headers = new ArrayList<>();
////        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
////        headers.add(new BasicHeader("Accept-Charset", "big5, big5-hkscs, cesu-8, euc-jp, euc-kr, gb18030, gb2312, gbk,iso-8859-1,utf-16, utf-16be, utf-16le, utf-32, utf-32be, utf-32le, utf-8"));
////        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
////        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
//        headers.add(new BasicHeader("Connection", "close"));
////
//        httpClientBuilder.setDefaultHeaders(headers);
//
//        CloseableHttpClient httpClient = httpClientBuilder.build();
//
//        // httpClient连接配置，底层是配置RequestConfig
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//
//        // 连接超时
//        clientHttpRequestFactory.setConnectTimeout(1000 * 5);
//        // 数据读取超时时间，即SocketTimeout
//        clientHttpRequestFactory.setReadTimeout(1000 * 20);
//        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
//        clientHttpRequestFactory.setConnectionRequestTimeout(200);
//        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
//        clientHttpRequestFactory.setBufferRequestBody(false);
//
//        // 添加内容转换器
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        messageConverters.add(new FormHttpMessageConverter());
////        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//
//        restTemplate = new RestTemplate(messageConverters);
//        restTemplate.setRequestFactory(clientHttpRequestFactory);
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
//
////        HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory)restTemplate.getRequestFactory();
////        requestFactory.getHttpClient().
//
//        log.info("RestClient初始化完成");
//    }

    private RestClient() {
    }

    public static RestTemplate getClient() {
        return getClient(HttpRestClient.DEFUAlT);
    }

    public static RestTemplate getClient(String poolName) {
        return getHttpClient(poolName).getRestTemplate();
    }

    public static HttpRestClient getHttpClient() {
        return getHttpClient(HttpRestClient.DEFUAlT);
    }

    public static HttpRestClient getHttpClient(String poolName) {
        if (poolName == null)
            poolName = HttpRestClient.DEFUAlT;

        HttpRestClient client = clients.get(poolName);
        if (client == null) {
            client = new HttpRestClient(poolName);
            clients.put(poolName, client);
        }
        return client;
    }
}