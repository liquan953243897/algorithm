package com.pgz.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 睡眠排序
 *
 * @author liquan_pgz@qq.com
 * @date 2020-01-07
 */
public class SleepSort {

    public static void main(String[] args) {

        int[] arr = {1, 4, 7, 3, 8, 9, 2, 6, 5, 10};
        List<Integer> result = sort(arr);
        System.out.println(result.toString());
    }

    /**
     * 睡眠排序
     *
     * @param arr
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-01-07
     **/
    public static List<Integer> sort(int[] arr) {
        List<Integer> result = new ArrayList<>();
        //创建指定长度的线程数组
        SortThread[] sortThreads = new SortThread[arr.length];
        CountDownLatch latch = new CountDownLatch(arr.length);
        //指定每个线程数组的值
        for (int i = 0; i < sortThreads.length; i++) {
            sortThreads[i] = new SortThread(arr[i], result, latch);
        }
        //开启每个线程
        for (SortThread sortThread : sortThreads) {
            sortThread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}


class SortThread extends Thread {
    int s;
    List<Integer> result;
    CountDownLatch latch;

    public SortThread(int s, List<Integer> result, CountDownLatch latch) {
        this.s = s;
        this.result = result;
        this.latch = latch;
    }

    public void run() {
        try {
            //睡眠指定的时间
            sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //输出该数
        result.add(s);
        System.out.println(s);
        latch.countDown();
    }
}
