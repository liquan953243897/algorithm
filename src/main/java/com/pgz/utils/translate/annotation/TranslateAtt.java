package com.pgz.utils.translate.annotation;

import com.pgz.utils.translate.constant.IConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Documented
@Inherited
public @interface TranslateAtt {

    //码值源
    String source() default IConfig.Source.DEFAULT;

    //码值类型
    String codeType() default "";

    //存码值转换后存放的字段名，为空默认放它本身上
    String valueFileName() default "";

}
