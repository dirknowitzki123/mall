package com.kingyon.chengxin.framework.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 设置“跨域”访问头
 * Created by Administrator on 17-3-21.
 */

@Configuration
public class WebappFilterConfig {

    @Bean
    public WebappFilter apiHeaderFilter() {
        return new WebappFilter();
    }

    @WebFilter(filterName = "webappFilter", urlPatterns = {"/api/*","boss/*"})
    public class WebappFilter implements Filter {
        @Override
        public void destroy() {

        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            if (response instanceof HttpServletResponse) {
                HttpServletResponse alteredResponse = ((HttpServletResponse) response);
                // I need to find a way to make sure this only gets called on 200-300 http responses
                // TODO: see above comment
                addHeadersFor200Response(alteredResponse);
            }

            filterChain.doFilter(request, response);
        }

        private void addHeadersFor200Response(HttpServletResponse response) {
            //TODO: externalize the Allow-Origin
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, token");
//            response.addHeader("Access-Control-Max-Age", "600000");
        }
    }
}
