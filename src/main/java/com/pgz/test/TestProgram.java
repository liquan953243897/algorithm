package com.pgz.test;

import java.util.Comparator;

/**
 * 测试编程
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-26
 */
public class TestProgram {

    public static <T> T findMax(T[] arr, Comparator<? super T> cmp) {
        int maxIndex = 0;

        for (int i = 1; i<arr.length; i++) {
            if (cmp.compare(arr[i], arr[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return arr[maxIndex];
    }

    public static void main(String[] args) {
        String[] arr = {"zhangSan", "LDAEWEQ", "liquan_pgz"};
        //例用一个函数对象作为第2个参数传递给findMax，输出zhangSan
        System.out.println(findMax(arr, new CaseInsensitiveCompare()));
    }

    static class CaseInsensitiveCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

}
