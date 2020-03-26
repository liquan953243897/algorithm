package com.pgz.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的测试
 * 核心线程池的类是ThreadPoolExecutor，有四个常用构造方法
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-13
 */
public class ThreadPoolTest {

    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //线程池最大容纳线程数
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    //线程空闲后的存活时长
    private static final int KEEP_ALIVE_TIME = 30;

    @Test
    public void testThreadPool() {
        int size = 3;
        int maxSize = 10;
        //有四种常用阻塞队列
        //ArrayBlockingQueue    基于数组结构的有界阻塞队列，按FIFO排序任务
        //LinkedBlockingQueue   基于链表结构的阻塞队列，按FIFO排序任务，吞吐量通常要高于ArrayBlockingQueue
        //SynchronousQueue      一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue
        //PriorityBlockingQueue 具有优先级的无界阻塞队列
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,  MAXIMUM_POOL_SIZE, 30, TimeUnit.SECONDS, queue);

        System.out.println("输出线程池大小==" + executor.getPoolSize());
        for (int i = 0; i <= 7; i++){
            executor.execute(new RunnableImpl());
        }

//        executor.prestartAllCoreThreads();

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executor.getPoolSize());
    }

    static class RunnableImpl implements Runnable {

        @Override
        public void run() {
            System.out.println("hello PGZ, I am [" + Thread.currentThread().getName() + "]");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
