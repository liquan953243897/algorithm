package com.pgz.test;

import com.pgz.sort.ArrayBub;
import org.junit.Test;

/**
 * 排序测试
 *
 * @author liquan_pgz@qq.com
 * @date 2020-08-07
 */
public class TestSort {

    @Test
    public void testBubSort() {
        ArrayBub arrayBub = new ArrayBub(5);
        arrayBub.add(2);
        arrayBub.add(5);
        arrayBub.add(4);
        arrayBub.add(6);
        arrayBub.display();
        arrayBub.bubbleSort();
        arrayBub.display();
    }
}
