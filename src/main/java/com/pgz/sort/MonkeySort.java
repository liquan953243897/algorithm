package com.pgz.sort;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 猴子排序
 *
 * @author liquan_pgz@qq.com
 * @date 2020-01-07
 */
public class MonkeySort {

    private static int length = 8;
    private static int[] array = new int[length];
    private static int[] result = new int[length];

    static {
        generate();
    }

    /**
     * 检查数组是否有序
     *
     * @return
     */
    private static boolean checkOrder(int array[]) {
        for (int i = 1; i < length; i++) {
            if (array[i] <= array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private static void sort(int num, Random random) {
        Set<Integer> numberSet = new HashSet<>();
        System.out.println("第" + num + "次");
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(length);
            while (numberSet.contains(index)) {
                index = random.nextInt(length);
            }
            numberSet.add(index);
            int number = array[index];
            result[i] = number;
            System.out.print(" " + result[i]);
        }
        System.out.println();
    }

    private static void generate() {
        array[0] = 8;
        array[1] = 5;
        array[2] = 3;
        array[3] = 6;
        array[4] = 4;
        array[5] = 9;
        array[6] = 7;
        array[7] = 10;
//        array[8] = 1;
//        array[9] = 2;
    }

    public static void main(String[] args) {
        Random random = new Random();

        int num = 0;
        long startTime = System.currentTimeMillis();

        while (true) {
            sort(num++, random);
            boolean isOrder = checkOrder(result);
            if (isOrder) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("排序完成，耗时：" + (endTime - startTime) + " ms");

    }


}