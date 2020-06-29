package com.pgz.utils.util;

import com.pgz.utils.annotation.FieldMapper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean映射工具类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-29
 */
public class BeanMapper {

    /**
     * 映射集合
     *
     * @param list
     * @param tClass
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-29 19:29:47
     **/
    public static <T> List<T> beanMapper(List<Object> list, Class<T> tClass) {

        if (null == list) {
            return null;
        }

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        //初始化集合并设置容量
        List<T> retList = new ArrayList<>(list.size());
        for (Object o : list) {
            retList.add(beanMapper(o, tClass));
        }

        return retList;
    }

    /**
     * 字段映射处理
     *
     * @param source
     * @param tClass
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-11
     **/
    public static <T> T beanMapper(Object source, Class<T> tClass) {
        if (source == null) {
            return null;
        }

        T t = null;
        try {
            //创建映射对象实例
            t = tClass.newInstance();
            Field[] destFields = tClass.getDeclaredFields();
            Class<?> sourceClass = source.getClass();

            for (Field field : destFields) {
                FieldMapper fieldMapper = field.getAnnotation(FieldMapper.class);
                field.setAccessible(true);
                String fieldName = field.getName();

                Object value = null;

                //不加注解时直接通过字段名进行映射
                if (fieldMapper == null) {
                    Field sourceField = getField(sourceClass, fieldName);
                    if (sourceField == null) {
                        continue;
                    }
                    sourceField.setAccessible(true);
                    Object o = sourceField.get(source);
                    if (o != null) {
                        value = o;
                    }
                } else {

                    String format = fieldMapper.format();
                    String fieldName1 = fieldMapper.fieldName();
                    String[] kv = fieldMapper.kv();

                    //如果字段上配置了映射的字段名，取配置的字段名，否则取待映射字段名
                    if (!fieldName1.equals("") && !fieldName1.equals(fieldName)) {
                        fieldName = fieldName1;
                    }

                    //根据字段名获取源字段
                    Field sourceField = getField(sourceClass, fieldName);
                    if (sourceField == null) {
                        continue;
                    }

                    //暴力破解可访问
                    sourceField.setAccessible(true);

                    //获取源字段值
                    value = sourceField.get(source);
                    if (value == null) {
                        continue;
                    }

                    //如果配置了日期字段格式，进行日期格式映射
                    if (!"".equals(format)) {
                        value = dateMapper(format, value, field);
                    }

                    //处理键值对映射
                    if (kv.length > 0) {
                        Map<Object, String> map = arr2Map(kv);
                        value = map.get(value.toString());
                    }

                }
                //设置参数异常时，进行捕获
                try {
                    field.set(t, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 获取日期格式数据
     *
     * @param format
     * @param o
     * @param field
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-29 19:20:30
     **/
    private static Object dateMapper(String format, Object o, Field field)
            throws ParseException {
        Object value = null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //如果源字段是日期格式，则进行日期->字符串转换
        if (o instanceof Date) {
            value = sdf.format(o);
        } else if (field.getType().equals(Date.class)
                && o instanceof String) {
            //如果被映射字段格式是日期字段且源字段是字符串格式，则进行字符串->日期转换
            value = sdf.parse(o.toString());
        }

        return value;
    }

    /**
     * 获取字段
     *
     * @param cls
     * @param fieldName
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-11
     **/
    private static Field getField(Class<?> cls, String fieldName) {
        try {
            return cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        return null;
    }

    /**
     * 数组转map
     *
     * @param arr eg:{"key:value", "key1:value2"}、
     *            {"key-value", "key1-value2"}、{"key=value", "key1=value2"}
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-11
     **/
    private static Map<Object, String> arr2Map(String[] arr) {
        Map<Object, String> map = new HashMap<>();
        for (String s : arr) {

            String[] split = s.split(getRegex(s));
            if (split.length > 1) {
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    /**
     * 获取分隔符
     *
     * @param str
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-06-29 18:57:16
     **/
    public static String getRegex(String str) {
        String regex = "";
        if (str.contains(":")) {
            regex = ":";
        } else if (str.contains("-")) {
            regex = "-";
        } else if (str.contains("=")) {
            regex = "=";
        }

        return regex;
    }

}
