package com.pgz.utils.translate.core;

import com.pgz.entity.Student;
import com.pgz.utils.translate.annotation.TranslateAtt;
import com.pgz.utils.translate.interfaces.TranslatorDriver;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 翻译工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-23
 */
@Component
public final class TranslateUtil implements ApplicationContextAware {

    private static TranslateUtil translateUtil;

    private ApplicationContext applicationContext;

    private Map<String, TranslatorDriver> translatorDrivers;

    /**
     * 服务器在加载Servlet时，在构造函数之后执行，init方法之前执行
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-07-25
     **/
    @PostConstruct
    private void init() {
        translateUtil = this;
        translatorDrivers = applicationContext.getBeansOfType(TranslatorDriver.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 翻译处理类，可接受集合和对象
     *
     * @param target
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-07-04
     **/
    public static void translate(Object target) {

        if (target == null) {
            return;
        }

        Collection collect = new ArrayList<>();
        if (target instanceof Collection) {
            collect = (Collection<?>) target;
        } else {
            collect.add(target);
        }

        for (Object o : collect) {
            Class<?> aClass = o.getClass();

            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    TranslateAtt att = field.getAnnotation(TranslateAtt.class);
                    if (att == null || StringUtils.isEmpty(att.source())) {
                        continue;
                    }
                    //获取字段值
                    Object value = field.get(o);
                    //码值转为字符串
                    String code = String.valueOf(value);
                    //获取注解上数据源类型
                    String source = att.source();
                    //获取码值类型
                    String codeType = att.codeType();
                    //翻译后的码值所放字段名
                    String valueFileName = att.valueFileName();

                    if (StringUtils.isEmpty(code)) {
                        continue;
                    }

                    //通过实例化的名字获取翻译驱动的对象
                    TranslatorDriver driver = translateUtil.translatorDrivers.get(source);

                    if (driver == null) {
                        continue;
                    }

                    //获取翻译后的值
                    String transValue = driver.translate(codeType, code);

                    Field valueField = field;
                    if (StringUtils.isEmpty(valueFileName)) {
                        if (!(value instanceof String)) {
                            continue;
                        }
                    } else {
                        valueField = aClass.getField(valueFileName);
                    }
                    valueField.set(o, transValue);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
