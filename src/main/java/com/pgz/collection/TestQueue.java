package com.pgz.collection;

import com.pgz.entity.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
        System.out.println(Integer.toHexString(15));
        System.out.println(1 & 15);

        List<String> list = new ArrayList<>();
        list.add("22");
        list.add("24");
        list.add("25");
        list.remove(1);
        System.out.println(list);

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("dd");
        linkedList.add("bb");
        String a = linkedList.get(1);
        linkedList.remove(0);
        System.out.println(linkedList);
    }

}
