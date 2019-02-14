package com.kingyon.chengxin.framework.annotation;

import com.alibaba.dubbo.config.annotation.Service;

import java.lang.annotation.*;

/**
 * Created by pengjunxi on 2018/7/21.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited

@Service(version = "${chengxin.service.version}", application = "${dubbo.application.id}", protocol = "${dubbo.protocol.id}", registry = "${dubbo.registry.id}")
public @interface DubboService {
    String value() default "";
}
