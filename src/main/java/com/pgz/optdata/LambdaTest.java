package com.pgz.optdata;

import com.pgz.entity.Student;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * lambda表达式演练
 *
 * @author liquan_pgz@qq.com
 * @date 2020-04-01
 */
public class LambdaTest {

    @Test
    public void test1() {
        List<String> values = Arrays.asList("1","2","3","4");
        System.out.println(add(values));
    }

    public static int add(List<String> values){
        values.parallelStream().forEach(System.out::println);
        return values.parallelStream().mapToInt(Integer::parseInt).sum();//mapToInt方法返回的是一个int的Stream，这样，再次调用stream.sum()得到和
    }

    public static int addMap(List<Student> list) {
        return list.parallelStream().mapToInt(Student::getAge).sum();
    }
}
