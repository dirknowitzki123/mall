package com.kingyon.chengxin.product.web.config;



import com.kingyon.chengxin.framework.config.AppConfig;
import com.kingyon.chengxin.framework.util.IpUtil;
import com.kingyon.chengxin.product.web.interceptor.AuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by leo on 16/6/13.
 * 初始化Web 拦截器bean
 */

@Configuration
@Slf4j
public class WebInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AppConfig appConfig;
    @Autowired
    AuthInterceptor authInterceptor;


    @Bean(name = "authInterceptor")
    public AuthInterceptor authInterceptor() {
        AuthInterceptor authInterceptor = new AuthInterceptor();
        log.info("注入 authInterceptor");
        return authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor );
    }

}
