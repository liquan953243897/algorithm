package com.pgz.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author liquan_pgz@qq.com
 * @date 2020-08-07
 */
public class ArrayBub {

    private long[] elem;

    private int nElem;

    public ArrayBub() {
    }

    public ArrayBub(int size) {
        elem = new long[size];
        nElem = 0;
    }

    public void add(long e) {
        elem[nElem] = e;
        nElem++;
    }

    public void display() {
        System.out.println(Arrays.toString(elem));
    }

    /**
     * 冒泡排序
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
                    elem[in] = b;
                    elem[in + 1] = a;
                }
            }
        }
    }

}
