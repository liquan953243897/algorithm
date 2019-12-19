package com.pgz.optdata;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 操作一下队列看看
 *
 * @author liquan@syxysoft.com
 * @date 2019-12-18
 */
public class UserTask {

    //队列大小
    private final int QUEUE_LENGTH = 10000 * 10;
    //基于内存的阻塞队列
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_LENGTH);
    //创建计划任务执行器
    private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

    /**
     * 构造函数，执行execute方法
     */
    public UserTask() {
        execute();
    }

    /**
     * 添加信息至队列中
     *
     * @param content
     */
    public void addQueue(String content) {
        queue.add(content);
    }

    /**
     * 初始化执行
     */
    public void execute() {
        //每一分钟执行一次
        es.scheduleWithFixedDelay(() -> {
            try {
                String content = queue.take();
                //处理队列中的信息。。。。。
                System.out.println(content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);

    }

    public static void main(String[] args) {
        testQueuePut();
    }

    /**
     * 玩一下队列的put方法
     *
     * @return
     * @author liquan@syxysoft.com
     * date 2019-12-18
     **/
    public static void testQueuePut() {
        //基于内存的阻塞队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.add("A");
        queue.add("B");
        Scanner scan = new Scanner(System.in);
        System.out.println("输入数据：");
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleWithFixedDelay(() -> {
            if (queue.size() == 2) {
                String remove = queue.remove();
                System.out.println("满了就移除数据：" + remove);
            }
        }, 0, 10, TimeUnit.SECONDS);
        int a = 1;
        while (scan.hasNextLine()) {
            a++;
            String string = scan.nextLine();
            System.out.println("阻塞了自己进来的：" + string + a);
            if (string.equals("put")) {
                try {
                    queue.put(string + a);
                    System.out.println(queue.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (string.equals("ls")) {
                System.out.println("当前队列数据是：" + queue.toString());
            }

            if (string.startsWith("remove")) {
                queue.remove();
            }

            if (string.startsWith("peek")) {
                String peek = queue.peek();
                System.out.println("peek的数据为：" + peek);
            }

            if (string.startsWith("take")) {
                String take = null;
                try {
                    take = queue.take();
                    System.out.println("take的数据为：" + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        scan.close();
    }

    @Test
    public void testQueue() {
        //add()和remove()方法在失败的时候会抛出异常(不推荐)
        Queue<String> queue = new LinkedList<>();
        //添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        for (String q : queue) {
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素，并在队列中删除
        System.out.println("poll=" + queue.poll());
        for (String q : queue) {
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素
        System.out.println("element=" + queue.element());
        for (String q : queue) {
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素
        System.out.println("peek=" + queue.peek());
        for (String q : queue) {
            System.out.println(q);
        }
    }


}
