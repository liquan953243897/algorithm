package com.pgz.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author liquan_pgz@qq.com
 * @date 2021-03-17
 */
public class TestK {
    //输入整数数组
    //输出出现次数最多的整数
    @Test
    public void test1() {
        int[] arr = new int[]{2, 2, 2, 3, 3, 44, 1};
        System.out.println(outMaxCount(arr));
    }

    public static int outMaxCount(int[] ints) {

        Map<Integer, Integer> map = new HashMap<>();
        for (Integer value : ints) {
            Integer integer = map.get(value);
            if (integer == null) {
                map.put(value, 1);
            } else {
                map.put(value, ++integer);
            }
        }

        AtomicReference<Integer> tem = new AtomicReference<>(0);
        AtomicInteger val = new AtomicInteger();
        map.forEach((key, value) -> {
            if (value > tem.get()) {
                tem.set(value);
                val.set(key);
            }
        });

        return val.get();
    }

    @Test
    public void testMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(30, "ee");
        map.put(14, "bb");
        map.put(14, "bb");
    }

    /**
     * 字符串，abcd 反转为 dcba，递归
     */
    private String reverseStr(String str) {
        if (str.length() <= 1) {
            return str;
        }

        return reverseStr(str.substring(1)) + str.charAt(0);
    }

    @Test
    public void testMaxLength() {
        String str = "a1 123# 1231#  abc";
        System.out.println(getMaxLength(str));
    }

    public int getMaxLength0(String str) {
        String[] split = str.split(" ");
        int maxLength = 0;
        for (String s : split) {
            int length = s.trim().length();
            maxLength = Math.max(length, maxLength);
        }
        return maxLength;
    }

    public int getMaxLength1(String str) {
        int maxLength = 0;
        int length = 0;
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar != ' ') {
                length++;
            } else {
                maxLength = Math.max(length, maxLength);
                length = 0;
            }
        }
        return maxLength;
    }

    public int getMaxLength(String str) {
        int maxLength = 0;
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                length++;
            } else {
                maxLength = Math.max(length, maxLength);
                length = 0;
            }
        }
        return maxLength;
    }

    @Test
    public void testGroup() {
        int[] inst = new int[]{1, 1, 2, 2, 2, 3, 4, 5};
        System.out.println(Arrays.toString(inst));
        int group = group(inst);
        System.out.println(group);
        System.out.println(Arrays.toString(inst));
    }

    public int group(int[] ints1) {
        if (ints1.length == 1) {
            return 1;
        }
        //重复后索引
        int index = 1;
        for (int i = 0; i < ints1.length - 1; i++) {
            if (ints1[i] != ints1[i + 1]) {
                ints1[index] = ints1[i + 1];
                index++;
            }
            System.out.println(Arrays.toString(ints1));
        }
        return index;
    }

    @Test
    public void testRemove() {
        int[] ints = new int[]{1, 1, 2, 2, 2, 3, 4, 5};
        System.out.println(remove(ints));
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int newI = 0;
        for (int curr = 1; curr < nums.length; curr++) {
            if (nums[curr] != nums[newI]) {
                newI++;
                nums[newI] = nums[curr];
                System.out.println(Arrays.toString(nums));
            }
        }
        return newI + 1;
    }

    /**
     * 有序数组 把不重复的数据 有序的放在最前面 返回不重复的数据的数量
     * 通过慢指针和快指针来操作，快指针下的值与慢指针的值进行比较 不相等时往前一位设置不一样的值 直至最后一位
     *
     * @param nums
     * @return
     * @author liquan_pgz@qq.com
     * date 2021-03-26
     **/
    private int remove(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int s = 0;
        for (int f = 1; f < nums.length; f++) {
            if (nums[f] != nums[s]) {
                s++;
                nums[s] = nums[f];
            }
        }
        return s + 1;
    }

    @Test
    public void testMerge() {
        int[] a = {1, 4, 3, 6};
        int[] b = {2, 5, 7};
        System.out.println(Arrays.toString(merge(a, b)));
    }

    /**
     * 合并数组a和b到c中 保持有序
     *
     * @param a {1,4,3,6}
     * @param b {2,5,7}
     * @return
     * @author liquan_pgz@qq.com
     * date 2021-03-26
     **/
    private int[] merge(int[] a, int[] b) {
        int al = 0, bl = 0;
        int[] c = new int[a.length + b.length];
        for (int i = 0; i < c.length; i++) {
            if (al < a.length && bl < b.length) {
                if (a[al] > b[bl]) {
                    c[i] = b[bl];
                    bl++;
                } else {
                    c[i] = a[al];
                    al++;
                }
            } else if (al > a.length) {

            }
        }
        return c;
    }

    @Test
    public void testThread() {
        LQ lq = new LQ();
        lq.start();

        for (; ; ) {
            synchronized (this) {
                if (lq.isFlag()) {
                    System.out.println("有点儿东西");
                }
            }
        }
    }

    class LQ extends Thread {

        private boolean flag = false;

        public boolean isFlag() {
            return flag;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println("flag=" + flag);
        }
    }

    @Test
    public void testArray() {
        int i = 1;
        while (true) {
            boolean flag = false;
            try {
                i*=10;
                int[] arr = new int[Integer.MAX_VALUE - i];
                System.out.format("Successfully initialized an array with %,d elements .\n", Integer.MAX_VALUE - i);
            } catch (Throwable t) {
                flag = true;
            }
            if (!flag) {
                System.out.println(i);
                break;
            }
        }
    }
}
