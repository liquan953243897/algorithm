package com.pgz.thread;

/**
 * volatile演示
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-28
 */
public class VolatileTest {
    private static volatile boolean isOver = false;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (!isOver) ;
        });
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOver = true;
    }

    public static class VolatileExample {
        private int a = 0;
        private volatile boolean flag = false;

        public void writer() {
            a = 1;          //1
            flag = true;   //2
        }

        public void reader() {
            if (flag) {      //3
                int i = a; //4
                System.out.println(i);
            }
        }
    }
}
