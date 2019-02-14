package com.kingyon.chengxin.framework.net;

import org.springframework.http.HttpHeaders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */
public class HttpHeader {
    HttpHeaders headers = new HttpHeaders();

    public HttpHeader() {
    }

    public HttpHeader(HttpHeaders headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        /**加到默认头，以便get请求*/
        List<String> head = headers.get(key);

        if (head == null ) {
            headers.add( key, value );
        } else {
            head.clear();
            head.add(value);
        }
    }

    HttpHeaders getHeaders() {
        return headers;
    }

    HttpHeader copyHttpHeader() {
        HttpHeaders cur = new HttpHeaders();
        Iterator keys = headers.entrySet().iterator();

        while(keys.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)keys.next();
            cur.add(entry.getKey(), entry.getValue().get(0));
        }
        return new HttpHeader(cur);
    }
}
