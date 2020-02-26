package com.pgz.test;

import com.pgz.entity.Student;
import com.pgz.entity.Teacher;
import org.junit.Test;

/**
 * 一些错误示范
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-25
 */
public class ErrorDemo {

    @Test
    public void newTwoObject() {
        Object o;
        o = new Student();
//        o = new Teacher();
        o = new String();

        Student student;
        student = new Student();
//        student = new Teacher();
        System.out.println(o);
    }

    @Test
    public void valuation0() {
        Student demo1, demo2, demo3;//创建多个对象引用，都存储在栈中
        demo1 = new Student();//创建一个Demo对象，存储在堆中，并将demo1指向这个对象，相当于加了一个链
        demo2 = demo1;//demo2与demo1一样，都指向堆中Demo对象
        demo3 = demo2;//demo3与demo2一样，都指向堆中Demo对象

        System.out.println("demo1= " + demo1);
        System.out.println("demo2= " + demo2);
        System.out.println("demo3= " + demo3);
    }

    @Test
    public void valuation() {
        Student ut = new Student();  // ut是引用，实际的对象在内存里。
        ut = new Student(); /*现在ut是另一个对象的引用，先前的对象被垃圾回收了（因为先前的对象不能被再次使用了）。*/
        Student ut2;  // 定义了一个引用ut2，他不引用任何对象，不能使用。。。。
        ut2 = new Student(); // 然ut2成为一个对象的引用。
        Student ut3 = new Student();
        Student ut4 = new Student();
        ut3 = ut4;   // 现在ut3引用ut4的对象，这里不是赋值。。。
        int a = 5;
        int b = 4;
        a = b;  // 这里是赋值。 a b 依然引用不同的对象

    }

}
