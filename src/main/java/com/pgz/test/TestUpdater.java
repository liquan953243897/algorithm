package com.pgz.test;

import com.pgz.entity.Student;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater的用法
 *
 * @author liquan_pgz@qq.com
 * @date 2021-03-10
 */
public class TestUpdater {

    @Test
    public void test1() {
//        AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        Student student = new Student();
        student.setHome("ddd");
//        updater.compareAndSet(student, student.getName(), "dd");
        System.out.println(student.getHome());
    }
}
