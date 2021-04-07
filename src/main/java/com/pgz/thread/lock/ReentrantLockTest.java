package com.pgz.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 非公平锁
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-29
 */
public class ReentrantLockTest {

    private static final Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        new Thread(ReentrantLockTest::test, "线程B").start();
        new Thread(ReentrantLockTest::test, "线程A").start();
        lock.newCondition();
    }

    public static void test() {
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();
        }
    }
}
