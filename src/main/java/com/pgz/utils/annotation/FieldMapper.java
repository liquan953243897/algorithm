package com.pgz.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapper {

    String format() default "";

    /**
     * 键值对存储映射关系 eg:{"key:value", "key1:value2"}、
     * {"key-value", "key1-value2"}、{"key=value", "key1=value2"}
     */
    String[] kv() default {};

    String fieldName() default "";
}
