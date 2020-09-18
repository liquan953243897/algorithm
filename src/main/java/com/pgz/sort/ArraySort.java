package com.pgz.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author liquan_pgz@qq.com
 * @date 2020-08-07
 */
public class ArraySort {

    private long[] elem;

    private int nElem;

    private int time;

    public ArraySort() {
    }

    public ArraySort(int size) {
        elem = new long[size];
        nElem = 0;
    }

    public void add(long e) {
        elem[nElem] = e;
        nElem++;
    }

    public void display() {
        System.out.println(Arrays.toString(elem));
        System.out.println("交换次数为==" + time);
    }

    /**
     * 冒泡排序
     * 依次比较，交换大值在右边
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-08-07 14:09:18
     **/
    public void bubbleSort() {
        int out, in;
        for (out = nElem - 1; out > 1; out--) {
            for (in = 0; in < out; in++) {
                long a = elem[in];
                long b = elem[in + 1];
                if (a > b) {
                    swap(in, in + 1);
                    time++;
                }
            }
        }
    }

    /**
     * 选择排序，选出最小的放在最左边，减少交换次数
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-08-07 14:33:39
     **/
    public void selectSort() {
        int out, in, min;
        for (out = 0; out < nElem - 1; out++) {
            min = out;
            for (in = out + 1; in < nElem; in++) {
                if (elem[in] < elem[min]) {
                    min = in;
                }
            }
            swap(min, out);
            time++;
        }
    }

    /**
     * 插入排序，假定一部分为有序数组，用标记的一项与有序数组比较
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-08-07 15:31:31
     **/
    public void insertionSort() {
        int in, out;
        for (out = 1; out < nElem; out++) {
            long temp = elem[out];
            in = out;
            while (in > 0 && elem[in - 1] >= temp) {
                elem[in] = elem[in - 1];
                in--;
                time++;
            }
            elem[in] = temp;
        }
    }


    private void swap(int a, int b) {
        long temp = elem[a];
        elem[a] = elem[b];
        elem[b] = temp;
    }

}
