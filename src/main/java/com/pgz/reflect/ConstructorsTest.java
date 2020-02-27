package com.pgz.reflect;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 测试类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-26
 */
public class ConstructorsTest {

    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.pgz.reflect.Student");

        System.out.println("**********************所有公有构造方法*********************************");

        Constructor[] constructors = clazz.getConstructors();

        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("**********************所有公有构造方法(包括：私有、受保护、默认、公有)*********************************");
        constructors = clazz.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        Method main = clazz.getMethod("main", String[].class);
        main.invoke(null, (Object) new String[]{"a", "b"});

        Method doSth = clazz.getMethod("doSth", String.class, Integer.class);
        doSth.invoke(new Student(), "字符串", 1);
    }

    @Test
    public void test1() throws Exception {
        //通过反射获取Class对象
        //"cn.fanshe.Student"
        Class stuClass = Class.forName(getValue("className"));
        //2获取show()方法
        //show
        Method m = stuClass.getMethod(getValue("methodName"), String.class, Integer.class);
        //3.调用show()方法
        m.invoke(stuClass.getConstructor().newInstance(), null, null);
    }

    //静态资源只加载一次
    static Properties pro;

    static {
        pro = new Properties();//获取配置文件的对象
        FileReader in;//获取输入流
        try {
            in = new FileReader("E:\\IDEA\\OWN\\Algorithm\\algorithm\\src\\main\\resources\\file\\pro.txt");
            pro.load(in);//将流加载到配置文件对象中
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //此方法接收一个key，在配置文件中获取相应的value
    public static String getValue(String key) throws IOException {
        return pro.getProperty(key);//返回根据key获取的value值
    }
}
