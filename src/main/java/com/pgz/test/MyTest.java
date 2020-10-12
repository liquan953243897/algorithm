package com.pgz.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Test
    public void testGetTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(new Date()));
    }

    @Test
    public void testByteArr() {
        byte[] ba = {'%', 'w', 1, 3, 31};
        System.out.println(ba);
        for (byte b : ba) {
            System.out.printf("%02x",b);//257701031f
        }
    }

    @Test
    public void testHex2Byte() {
        System.out.println((byte)Integer.parseInt("48",16));//31
    }

    @Test
    public void testByte2Str() {
        byte b = -86;

        System.out.println(new String(new byte[]{b}));
    }

    @Test
    public void testStr2Byte() {
        String str = "512";

        byte[] bytes = str.getBytes();
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }

    @Test
    public void testByteToHex() {
        byte b = -86;
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        System.out.println(hex);
    }
}
