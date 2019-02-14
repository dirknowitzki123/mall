package com.kingyon.chengxin.framework.annotation;

import com.alibaba.dubbo.config.annotation.Reference;

import java.lang.annotation.*;

/**
 * Created by pengjunxi on 2018/7/21.
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Inherited

@Reference(version = "${chengxin.service.version}",application = "${dubbo.application.id}",registry = "${dubbo.registry.id}")
public @interface DubboReference {
    String value() default "";
}
