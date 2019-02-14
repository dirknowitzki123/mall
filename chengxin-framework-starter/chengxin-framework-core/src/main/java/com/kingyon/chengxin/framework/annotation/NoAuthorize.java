package com.kingyon.chengxin.framework.annotation;

import java.lang.annotation.*;


/**
 * Created by Administrator on 16-9-2.
 *
 * 无需登录
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoAuthorize {
}
