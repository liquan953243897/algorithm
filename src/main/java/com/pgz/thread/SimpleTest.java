package com.pgz.thread;

import org.junit.Test;

/**
 * 简单的线程测试
 *
 * @author liquan_pgz@qq.com
 * @date 2020-12-21
 */
public class SimpleTest {


    @Test
    public void testInterrupt() throws InterruptedException {
        Thread haha = new Thread(() -> {
            while (true) {
                System.out.println("haha");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        haha.start();

        Thread.sleep(5000);

        haha.interrupt();
    }

}
