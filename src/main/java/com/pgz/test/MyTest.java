package com.pgz.test;

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

}
