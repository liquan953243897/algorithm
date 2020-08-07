package com.pgz.test;

import com.pgz.sort.ArraySort;
import org.junit.Test;

/**
 * 排序测试
 *
 * @author liquan_pgz@qq.com
 * @date 2020-08-07
 */
public class TestSort {

    /**
     * 冒泡排序
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-08-07 14:35:42
     **/
    @Test
    public void testBubSort() {
        ArraySort arrayBub = new ArraySort(5);
        arrayBub.add(5);
        arrayBub.add(4);
        arrayBub.add(3);
        arrayBub.add(2);
        arrayBub.display();
        arrayBub.bubbleSort();
        arrayBub.display();
    }

    /**
     * 选择排序
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-08-07 14:35:50
     **/
    @Test
    public void testSelSort() {
        ArraySort arrayBub = new ArraySort(5);
        arrayBub.add(5);
        arrayBub.add(4);
        arrayBub.add(3);
        arrayBub.add(2);
        arrayBub.display();
        arrayBub.selectSort();
        arrayBub.display();
    }

    @Test
    public void testInsertSort() {
        ArraySort arrayBub = new ArraySort(5);
        arrayBub.add(5);
        arrayBub.add(4);
        arrayBub.add(3);
        arrayBub.add(2);
        arrayBub.add(1);
        arrayBub.display();
        arrayBub.insertionSort();
        arrayBub.display();
    }
}
