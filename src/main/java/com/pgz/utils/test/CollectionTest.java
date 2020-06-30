package com.pgz.utils.test;

import cn.hutool.core.lang.Console;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.pgz.utils.util.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合测试类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-06-29
 */
public class CollectionTest {

    private static final List<Student> LIST_STUDENT;

    private static final Student S_A = new Student("张三", 18);

    private static final Student S_B = new Student("李四", 18);

    private static final Student S_C = new Student("王五", 18);

    private static final Student S_D = new Student("钱六", 18);

    static {
        LIST_STUDENT = ImmutableList.of(S_A, S_B, S_C, S_D);
    }

    @Test
    public void testSort() {
        List<Object> orderRegulation = Arrays.asList("王五", "李四", "张三", "钱六");

        List<Student> list = new ArrayList<>(LIST_STUDENT);
        CollectionUtil.sort(orderRegulation, list, Student::getName);

        Console.print(list);
        String join = Joiner.on(',').join(orderRegulation);

        Console.print(join);

    }


    @Data
    @AllArgsConstructor
    static
    class Student {
        private String name;

        private Integer age;
    }

}
