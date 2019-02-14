package com.kingyon.chengxin.framework.annotation;

import java.lang.annotation.*;


/**
 *
 * 字段属性名（用于对象复制时，标记不同的字段名的值来源）
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapped {

    String[] names() default {};
}
