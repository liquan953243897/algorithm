package com.pgz.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-21
 */
public class MyTest {

    /**
     * Integer 内部存在缓存 -128~127
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-09-21
     **/
    @Test
    public void testInteger() {
        Integer a = 1000, b = 1000;
        System.out.println(a == b);
        Integer c = 100, d = 100;
        System.out.println(c == d);
    }

    /**
     * leftPad 左边填充数据
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-09-23
     **/
    @Test
    public void testByte() {
        byte byteValue = '\'';
        // 将byte转换为8位二进制字符串 依赖 commons-lang-x.x.jar包
        System.out.println(byteValue & 0xff);
        String binaryString = StringUtils.leftPad(Integer.toBinaryString(byteValue & 0xff), 8, '0');
        System.out.println(binaryString);
    }

    @Test
    public void testPad() {
        String a = "abc";
        String b = "ab";
        System.out.println(StringUtils.center(a, 7, "*"));
    }

}
