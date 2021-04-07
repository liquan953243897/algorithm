package com.pgz.thread;

import sun.misc.Unsafe;


/**
 * Unsafe演示
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-30
 */
public class UnsafeTest {

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private volatile int state;

    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        test.state = 1;
        unsafe.compareAndSwapInt(test, stateOffset, 1, 2);
        System.out.println(test.state);
    }
}
