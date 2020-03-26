package com.pgz.thread;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * 多线程的一些示例
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-29
 */
public class MultithreadedDemo {

    private static final int MAX_PRODUCT = 10;

    private static final int MIN_PRODUCT = 5;

    private int product = 0;

    /**
     * 生产者生产出来的产品交给店员
     */
    public synchronized void produce() {
        if (this.product >= MAX_PRODUCT) {
            try {
                wait();
                System.out.println("产品已满,请稍候再生产");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        this.product++;
        System.out.println("生产者生产第" + this.product + "个产品.");
        notifyAll();   //通知等待区的消费者可以取出产品了
    }

    /**
     * 消费者从店员取产品
     */
    public synchronized void consume() {
        if (this.product <= MIN_PRODUCT) {
            try {
                wait();
                System.out.println("缺货,稍候再取");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        System.out.println("消费者取走了第" + this.product + "个产品.");
        this.product--;
        notifyAll();   //通知等待去的生产者可以生产产品了
    }

    /**
     * https://www.jianshu.com/p/25e243850bd2 你真的懂wait、notify和notifyAll吗
     *
     */
    public static void main(String[] args) {
        MultithreadedDemo demo = new MultithreadedDemo();
        //生产
        Thread produce = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                demo.produce();
            }
        });

        //消费
        Thread consume = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                demo.consume();
            }
        });

        produce.start();
        consume.start();
    }

    @Test
    public void testThread() {
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });

        t1.start();
        t2.start();
    }

}
