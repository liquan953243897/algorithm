package com.pgz.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConditionTest
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-30
 */
public class ConditionTest2 {

    static class Service {
        private final ReentrantLock lock = new ReentrantLock();       //定义锁对象
        //定义两个Condition对象
        private final Condition conditionA = lock.newCondition();
        private final Condition conditionB = lock.newCondition();

        //定义方法,使用conditionA等待
        public void waitMethodA() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " begin wait:" + System.currentTimeMillis());
                conditionA.await();         //等待
                System.out.println(Thread.currentThread().getName() + " end wait:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        //定义方法,使用conditionB等待
        public void waitMethodB() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " begin wait:" + System.currentTimeMillis());
                conditionB.await();         //等待
                System.out.println(Thread.currentThread().getName() + " end wait:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        //定义方法唤醒conditionA对象上的等待
        public void signalA() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " signal A time = " + System.currentTimeMillis());
                conditionA.signal();
                System.out.println(Thread.currentThread().getName() + " signal A time = " + System.currentTimeMillis());
            } finally {
                lock.unlock();
            }
        }

        //定义方法唤醒conditionB对象上的等待
        public void signalB() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " signal B time = " + System.currentTimeMillis());
                conditionB.signal();
                System.out.println(Thread.currentThread().getName() + " signal B time = " + System.currentTimeMillis());
            } finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();

        //开启两个线程,分别调用waitMethodA(),waitMethodB()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.waitMethodA();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.waitMethodB();
            }
        }).start();

        Thread.sleep(3000);         //main线程睡眠3秒
        service.signalA();          //唤醒 conditionA对象上的等待,conditionB上的等待依然继续等待
        service.signalB();
    }
}
