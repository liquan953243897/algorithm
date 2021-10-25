package com.pgz.test;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wb_liquan02
 * @copyright: sankuai.com
 * @date 2021/8/9
 */
@Slf4j
@RestController
@RequestMapping("test")
public class ApiTestController {

    @Autowired
    WebApplicationContext context;

    @PostMapping("universalTest")
    public Object universalTest(@RequestBody Param param) throws Exception {
        String name = param.getService();
        String f = String.valueOf(name.charAt(0));
        name = name.replaceFirst(f, f.toLowerCase(Locale.ROOT));
        Object bean;
        if (context.containsBean(name)) {
            bean = context.getBean(name);
        } else {
            bean = context.getBean(param.getService());
        }

        if (AopUtils.isAopProxy(bean)) {
            if (AopUtils.isJdkDynamicProxy(bean)) {
                bean = getJdkDynamicProxyTargetObject(bean);
            } else {
                bean = getCglibProxyTargetObject(bean);
            }
        }

        Collection<Method> methods = getMethods(bean);

        Object invoke = bean;
        boolean flag = true;
        for (Method method : methods) {
            if (method.getName().equals(param.getApi()) && method.getParameterCount() == param.getParams().length) {
                method.setAccessible(true);
                invoke = method.invoke(bean, typeOf(param.getParams(), method.getParameters()));
                flag = false;
                break;
            }
        }
        if (flag) {
            log.error("未找到可调用的方法");
        }

        return invoke;
    }

    private Map<String, Field> getFields(Object obj) {
        Class<?> cls = obj.getClass();
        return getFields(cls);
    }

    private List<Method> getMethods(Object obj) {
        Class<?> cls = obj.getClass();
        return getMethods(cls);
    }

    private Map<String, Field> getFields(Class<?> cls) {
        List<Field> fieldList = new ArrayList<>();
        while (cls != null) {
            fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        return fieldList.stream().collect(Collectors.toMap(Field::getName, field -> field, (k1, k2) -> k2));
    }

    private List<Method> getMethods(Class<?> cls) {
        List<Method> methodList = new ArrayList<>();
        while (cls != null) {
            methodList.addAll(Arrays.asList(cls.getDeclaredMethods()));
            cls = cls.getSuperclass();
        }
        return methodList;
    }

    private Object[] typeOf(Object[] objArray, Parameter[] parameters) {
        if (Objects.isNull(objArray)) {
            return new Object[0];
        }
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < objArray.length && i < parameters.length; i++) {
            Object obj = objArray[i];
            Object value = null;
            if (obj != null) {
                String jsonStr = JSONUtil.toJsonStr(obj);
                Parameter parameter = parameters[i];
                Class<?> type = parameter.getType();
                if (obj instanceof List) {
                    Type parameterizedType = parameter.getParameterizedType();
                    if (parameterizedType instanceof ParameterizedType) {
                        ParameterizedType p = (ParameterizedType)parameterizedType;
                        Type[] actualTypeArguments = p.getActualTypeArguments();
                        type = (Class<?>) actualTypeArguments[0];
                    }
                    value = JSONUtil.toList(JSONUtil.parseArray(jsonStr), type);
                } else {
                    value = JSONUtil.toBean(jsonStr, type);
                }
            }

            objects[i] = value;
        }

        return objects;
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy)h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

    @Data
    static
    class Param {
        private String service;

        private String api;

        private Object[] params;
    }
}
