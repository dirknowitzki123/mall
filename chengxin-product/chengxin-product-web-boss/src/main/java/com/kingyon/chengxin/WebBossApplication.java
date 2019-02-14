package com.kingyon.chengxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages={"com.kingyon.chengxin"})
@SpringBootApplication
public class WebBossApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBossApplication.class, args);
    }
}