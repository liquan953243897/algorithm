package com.pgz.test;

import org.junit.Test;

/**
 * 列举一些练习题
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-28
 */
public class Exercises {

    /**
     * 八种基本类型：
     * byte     2^7-1   8位二进制   2位十六进制  7F
     * short    2^15-1  16位二进制  4位十六进制  7FFF
     * int      2^31-1  32位二进制  8位十六进制  7FFFFFFF
     * long     2^63-1  64位二进制  16位十六进制 7FFFFFFFFFFFFFFF
     * float double
     * boolean char
     */
    @Test
    public void testShort() {
        short a = 1;

        short b = 1;

        short c = 0;

//        a = a + b;//编译失败

//        c = a + b;//编译失败

//        a = a + 1;//编译失败
        a = (short) (a + 1);

        a++;//++是一个运算符，进行了强制类型转换

        a += 1;//+=是一个运算符，进行了强制类型转换

        System.out.println(a);
    }

    /**
     * short范围内最大值 (2^15) - 1 = 32767
     *
     * @author liquan_pgz@qq.com
     * date 2020-02-28
     **/
    @Test
    public void testShortLength() {
//        short s = 32768;

        short s = 0x7FFF;//short 0x7FFF int 0x7FFFFFFF long 0x7FFFFFFFFFFFFFFFL

        long l = 0x7FFFFFFFFFFFFFFFL;

        System.out.println(Integer.toHexString(32767));

        System.out.println(Long.toHexString(0x7FFFFFFFFFFFFFFFL));

        s = (short) (s + 10);

        System.out.println(s);//超出范围会变负的

    }

    /**
     * 位置数值大小时编译器会默认按int处理
     *
     * @author liquan_pgz@qq.com
     * date 2020-02-28
     **/
    @Test
    public void testByte() {
//        byte a = 128;
//        byte a = 127;//最大127

        byte a = 0x7F;//最大127

        byte b = 2;

        byte c = 1 + 2;//在编译的时候（编译器javac），已经确定了 1+2 的结果并没有超过byte类型的取值范围，可以赋值给变量 c ，因此 c=1 + 2 是正确的

//        byte d = b + c;//b和c是变量，变量的值是可能变化的，在编译的时候，编译器javac不确定b+c的结果是什么，因此会将结果以int类型进行处理，所以int类型不能赋值给byte类型，因此编译失败。

        byte d = (byte) (b + c);//强制类型转换后是可以的

        System.out.println(c);

        System.out.println(d);
    }

    /**
     * 看一下for循环
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-02-28
     **/
    @Test
    public void testInt() {
        int a = 0;

        for (
                int i = 0;//单次表达式
                i >= a && i < 100;//条件表达式
                i++//末尾循环体，在执行完中间循环体后执行
        )
        //中间循环体
        {
            a += i;
            System.out.print("a = " + a + " ");
            System.out.println("i = " + i);
        }

    }

    /**
     * 看一下for循环
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-02-28
     **/
    @Test
    public void testInt1() {
        int a = 0;
        int i = 0;//单次表达式
        for (; i >= a && i < 100;//条件表达式
        )
        //中间循环体
        {
            a += i;
            i++;//末尾循环体，在执行完中间循环体后执行
            System.out.print("a = " + a + " ");
            System.out.println("i = " + i);
        }

        System.out.println(a);
    }

    /**
     * 当浮点数参与运算时，会都转换为浮点数进行计算
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-02-28
     **/
    @Test
    public void testDouble() {
        int x = 4;
        int ret;
//        ret = x > 4 ? 9.9 : 9;//Required type:int Provided:double
        System.out.println("value is " + (x > 4 ? 9.9 : 9));
    }
}
