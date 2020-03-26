package com.pgz.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * notify()方法被调用时，JVM会唤醒处于Wait Set中的某一个线程，这个线程的状态就从WAITING转变为RUNNABLE
 * notifyAll()方法被调用时，Wait Set中的全部线程会转变为RUNNABLE状态
 * <p>
 * java中对象锁模型:JVM会为一个使用内部锁（synchronized）的对象维护两个集合，Entry Set和Wait Set，也有人翻译为锁池和等待池，意思基本一致
 * <p>
 * 对于Entry Set：如果线程A已经持有了对象锁，此时如果有其他线程也想获得该对象锁的话，它只能进入Entry Set，并且处于线程的BLOCKED状态
 * 对于Wait Set：如果线程A调用了wait()方法，那么线程A会释放该对象的锁，进入到Wait Set，并且处于线程的WAITING状态
 *
 * 某个线程B想要获得对象锁，一般情况下有两个先决条件，
 * 一是对象锁已经被释放了（如曾经持有锁的前任线程A执行完了synchronized代码块或者调用了wait()方法等等）
 * 二是线程B已处于RUNNABLE状态
 *
 * 那么这两类集合中的线程都是在什么条件下可以转变为RUNNABLE呢？
 *
 * 对于Entry Set中的线程，当对象锁被释放的时候，JVM会唤醒处于Entry Set中的某一个线程，这个线程的状态就从BLOCKED转变为RUNNABLE。
 * 对于Wait Set中的线程，当对象的notify()/notifyAll()方法被调用时，JVM会唤醒处于Wait Set中的某一个/全部线程，这个线程的状态就从WAITING转变为RUNNABLE
 *
 *
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-29
 */
public class Something {
    private Buffer mBuf = new Buffer();

    public void produce() {
        synchronized (this) {
            while (mBuf.isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.add();
            //之所以我们应该尽量使用notifyAll()的原因就是，notify()非常容易导致死锁
            notifyAll();
        }
    }

    public void consume() {
        synchronized (this) {
            while (mBuf.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.remove();
            notifyAll();
        }
    }

    private static class Buffer {
        private static final int MAX_CAPACITY = 1;
        private List<Object> innerList = new ArrayList<>(MAX_CAPACITY);

        void add() {
            if (isFull()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.add(new Object());
            }
            System.out.println(Thread.currentThread().toString() + " add");

        }

        void remove() {
            if (isEmpty()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.remove(MAX_CAPACITY - 1);
            }
            System.out.println(Thread.currentThread().toString() + " remove");
        }

        boolean isEmpty() {
            return innerList.isEmpty();
        }

        boolean isFull() {
            return innerList.size() == MAX_CAPACITY;
        }
    }

    public static void main(String[] args) {
        Something sth = new Something();
        Runnable runProduce = new Runnable() {
            int count = 4;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.produce();
                }
            }
        };
        Runnable runConsume = new Runnable() {
            int count = 4;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.consume();
                }
            }
        };
        for (int i = 0; i < 2; i++) {
            new Thread(runConsume).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(runProduce).start();
        }
    }
}