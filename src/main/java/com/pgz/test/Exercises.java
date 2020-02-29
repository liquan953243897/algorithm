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

    /**
     * 比较String 和StringBuilder、StringBuffer的性能
     * 性能和速度维度比较 StringBuilder > StringBuffer > String
     * StringBuilder    线程不安全的，用在单线程多字符串操作的场景
     * StringBuffer     线程安全的，用在多线程多字符串操作的场景
     * String           少量字符串操作的场景
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-02-29
     **/
    @Test
    public void testString() {

        Long start1 = System.currentTimeMillis();//获取开始时间
        String str = "a";
        for (int i = 0; i < 100000; i++)//重复10万次进行String变量加操作
        {
            str += "b";
        }
        Long end1 = System.currentTimeMillis();//获取结束时间
        System.out.println("String花费时间：" + (end1 - start1));//打印出花费的时间

        Long start2 = System.currentTimeMillis();
        StringBuilder str2 = new StringBuilder("a");
        for (int i = 0; i < 100000; i++)//重复10万次进行StringBuilder变量加操作
        {
            str2.append("b");
        }
        Long end2 = System.currentTimeMillis();
        System.out.println("StringBuilder花费时间：" + (end2 - start2));

        Long start3 = System.currentTimeMillis();
        StringBuffer str3 = new StringBuffer("a");
        for (int i = 0; i < 100000; i++)//重复10万次进行StringBuffer变量加操作
        {
            str3.append("b");
        }
        Long end3 = System.currentTimeMillis();
        System.out.println("StringBuffer花费时间：" + (end3 - start3));
    }

    @Test
    public void testStr() {
        String string = "中华小曲库中华小曲库中华小曲库中华小曲库中华小曲库中华小曲库华小曲库";

        char[] chs = new char[100];
        chs = newChar();
        string.getChars(0, string.length(), chs, 10);

        for (char c : chs) {
            System.out.println(c);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(string);
        System.out.println(builder);
    }

    private char[] newChar() {
        return new char[1000];
    }

}
