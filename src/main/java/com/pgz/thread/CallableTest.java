package com.pgz.thread;

import com.pgz.entity.Student;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable接口实现
 * Callable与Runnable接口的相同点和不同点
 * 相同点：都是接口，都是用来编写多线程程序，两者都需要Thread里的start()启动线程
 * 不同点：实现Callable接口的任务线程可以返回线程执行结果，Runnable不可以
 *        Callable接口里的call()可以抛出异常，Runnable接口的run()只能内部消化，不能继续往上抛
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-13
 */
public class CallableTest implements Callable<Student> {

    private Student student;

    public CallableTest setStudent(Student student) {
        this.student = student;
        return this;
    }

    @Override
    public Student call() throws Exception {
        student.setName("张三");
        System.out.println(student.getName());
        return student;
    }

    public static void main(String[] arg) throws ExecutionException, InterruptedException {
        Callable<Student> callable = new CallableTest();

        FutureTask<Student> task = new FutureTask<>(callable);

        new Thread(task).start();

        Student student = task.get();

        System.out.println("student=" + student.getName());
        System.out.println("student=" + student.getName());
    }

    @Test
    public void testCallablePool() {
        ThreadPoolUtils poolUtils = new ThreadPoolUtils(ThreadPoolUtils.SINGLE_THREAD, 1);
        Future<Student> submit = poolUtils.submit(new CallableTest().setStudent(new Student()));
        submit.cancel(false);

    }
}
