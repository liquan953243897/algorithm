package com.pgz.polymorphic;

/**
 * 测试类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-26
 */
public class PolymorphicTest {

    public static void main(String[] args) {

        Ye y = new Ye();
        Ye y2 = new Fu(); //向上
        Fu f = new Fu();
        Zi z = new Zi() {
            public Zi dost() {
                System.out.println("匿名内部类");
                return new Zi();
            }
        }.dost();
        Sun s = new Sun();

        System.out.println("第一题 " + y.show(f));
        System.out.println("第二题 " + y.show(z));
        System.out.println("第三题 " + y.show(s));
        System.out.println("第四题 " + y2.show(f));  //到这里挂了？？？
        System.out.println("第五题 " + y2.show(z));
        System.out.println("第六题 " + y2.show(s));
        System.out.println("第七题 " + f.show(f));
        System.out.println("第八题 " + f.show(z));
        System.out.println("第九题 " + f.show(s));

        System.out.println(z.toString());

    }

}
