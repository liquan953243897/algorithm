package com.pgz.mode;

import java.util.concurrent.CountDownLatch;

/**
 * 懒加载单例
 *
 * @author liquan_pgz@qq.com
 * @date 2021/9/5 4:17 下午
 */
public class LazySingletonTest {

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println(LazySingleton.getInstance());
            cdl.countDown();
        }).start();
        new Thread(() -> {
            System.out.println(LazySingleton.getInstance());
            cdl.countDown();
        }).start();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LazySingleton.getInstance());
    }

}

class LazySingleton {
    private volatile static LazySingleton instance = null;

    private LazySingleton() {
        if (instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                    //字节码
                    //JIT、CPU会导致指令重排，通过加volatile描述保证指令不会重排
                    //1.分配空间
                    //2.初始化
                    //3.引用赋值

                }
            }
        }
        return instance;
    }
}