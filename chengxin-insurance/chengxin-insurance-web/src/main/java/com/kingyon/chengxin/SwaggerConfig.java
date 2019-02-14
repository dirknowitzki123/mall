
package com.kingyon.chengxin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Order(22)
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket testApi1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("chengxin1").genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// api测试请求地址
                .select()
                .paths(PathSelectors.regex("/api/.*"))// 过滤的接口
                .build()
                .apiInfo(testApiInfo());


    }
    @Bean
    public Docket testApi2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("chengxin2").genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// api测试请求地址
                .select()
                .paths(PathSelectors.regex("/serv/.*"))// 过滤的接口
                .build()
                .apiInfo(testApiInfo());
    }

    private ApiInfo testApiInfo() {
        Contact contact = new Contact("Aspen", "http://springfox.github.io/springfox/", "pengjunxi_x@163.com");
        ApiInfo apiInfo = new ApiInfo("标题", // 标题
                "描述", // 描述
                "0.1", // 版本
                "Url的服务条款", //Url的服务条款
                contact, // 作者
                "授权", // 授权信息
                ""// 授权URL
        );
        return apiInfo;
    }

}
