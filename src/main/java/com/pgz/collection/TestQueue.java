package com.pgz.collection;

import com.pgz.entity.Student;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 测试队列
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-29
 */
public class TestQueue {

    @Test
    public void test1() {
        Queue<String> q = new ArrayDeque<>();
        q.add("ddd");
        q.add("aaa");
        System.out.println(q.peek());
        System.out.println(q.poll());//ddd
        System.out.println(q.poll());//aaa
        System.out.println(q.poll());//null
    }

    @Test
    public void testYu() {
        System.out.println( Integer.toHexString(15));
        System.out.println(1&15);
    }

}
