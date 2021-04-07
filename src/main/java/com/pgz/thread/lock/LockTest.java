package com.pgz.thread.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁测试
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-12
 */
public class LockTest {
    private final Lock lock = new ReentrantLock();

    /*
     * 使用完毕释放后其他线程才能获取锁
     */
    public void lockTest(Thread thread) {
        lock.lock();//获取锁
        try {
            System.out.println("线程" + thread.getName() + "获取当前锁"); //打印当前锁的名称
            Thread.sleep(2);//为看出执行效果，是线程此处休眠2秒
        } catch (Exception e) {
            System.out.println("线程" + thread.getName() + "发生了异常释放锁");
        } finally {
            System.out.println("线程" + thread.getName() + "执行完毕释放锁");
            lock.unlock(); //释放锁
        }
    }

    @Test
    public void lockTest() {
        LockTest lockTest = new LockTest();
        //声明一个线程 “线程一”
        Thread thread1 = new Thread(() -> lockTest.lockTest(Thread.currentThread()), "thread1");
        //声明一个线程 “线程二”
        Thread thread2 = new Thread(() -> lockTest.lockTest(Thread.currentThread()), "thread2");
        // 启动2个线程
        thread1.start();
        thread2.start();
    }

    /*
     * 尝试获取锁 tryLock() 它表示用来尝试获取锁，如果获取成功，则返回true，
     * 如果获取失败（即锁已被其他线程获取），则返回false
     */
    public void tryLockTest(Thread thread) {
        //尝试获取锁
        boolean lock = this.lock.tryLock();
        System.out.println(lock);
        if (lock) {
            try {
                //打印当前锁的名称
                System.out.println("线程--" + thread.getName() + "获取当前锁");
                //为看出执行效果，是线程此处休眠2秒
                Thread.sleep(12000);
            } catch (Exception e) {
                System.out.println("线程--" + thread.getName() + "发生了异常释放锁");
            } finally {
                System.out.println("线程--" + thread.getName() + "执行完毕释放锁");
                this.lock.unlock(); //释放锁
            }
        } else {
            System.out.println("我是线程--" + Thread.currentThread().getName() + "当前锁被别人占用，我无法获取");
        }
    }

    @Test
    public void tryLockTest() {
        LockTest lockTest = new LockTest();

        Thread thread1 = new Thread(() -> lockTest.tryLockTest(Thread.currentThread()), "thread3");
        //声明一个线程 “线程二”
        Thread thread2 = new Thread(() -> lockTest.tryLockTest(Thread.currentThread()), "thread4");
        // 启动2个线程
        thread2.start();
        thread1.start();
    }

    /**
     * @param thread
     * @throws InterruptedException
     */
    public void tryLockParamTest(Thread thread) throws InterruptedException {
        //尝试获取锁 获取不到锁，就等3秒，如果3秒后还是获取不到就返回false
        if (lock.tryLock(3000, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println("线程" + thread.getName() + "获取当前锁"); //打印当前锁的名称
                Thread.sleep(4000);//为看出执行效果，是线程此处休眠2秒
            } catch (Exception e) {
                System.out.println("线程" + thread.getName() + "发生了异常释放锁");
            } finally {
                System.out.println("线程" + thread.getName() + "执行完毕释放锁");
                lock.unlock(); //释放锁
            }
        } else {
            System.out.println("我是线程" + Thread.currentThread().getName() + "当前锁被别人占用,等待3s后仍无法获取,放弃");
        }
    }

    /**
     * 测试带参数的tryLock
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-03-12
     **/
    @Test
    public void tryLockParamTest() {
        LockTest lockTest = new LockTest();
        Thread thread1 = new Thread(() -> {
            try {
                lockTest.tryLockParamTest(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1");
        //声明一个线程 “线程二”
        Thread thread2 = new Thread(() -> {
            try {
                lockTest.tryLockParamTest(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2");
        // 启动2个线程
        thread2.start();
        thread1.start();
    }


}
