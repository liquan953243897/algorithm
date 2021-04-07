package com.pgz.thread;

/**
 * 线程让步
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-30
 */
public class SubThread6 extends Thread {

    @Override
    public void run() {
        long begin = System.currentTimeMillis();
        long sum = 0;
        for(int i = 1; i <= 1000000; i++){
            sum += i;
            Thread.yield();         //线程让步, 放弃CPU执行权
        }
        long end = System.currentTimeMillis();
        System.out.println("用时: " + (end - begin) + "sum=" + sum);
    }

    public static void main(String[] args) {
        //开启子线程,计算累加和
        SubThread6 t6 = new SubThread6();
        t6.start();

        //在main线程中计算累加和
        long begin = System.currentTimeMillis();
        long sum = 0;
        for(int i = 1; i <= 1000000; i++){
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("main方法 , 用时: " + (end - begin) + "sum=" + sum);
    }
}
