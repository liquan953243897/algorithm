package com.pgz.mode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 静态内部类---懒加载，在调用getInstance()时，创建单例对象
 * 本质上是利用类加载机制保证线程安全
 * 只有在使用的时候，才会触发类的初始化，所以也是懒加载的一种形式
 *
 * @author liquan_pgz@qq.com
 * @date 2021/9/5 5:46 下午
 */
public class InnerClassSingletonTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        new Thread(() ->{
//            InnerClassSingleton instance2 = InnerClassSingleton.getInstance();
//            System.out.println(instance2);
//        }).start();
//
//        new Thread(() ->{
//            InnerClassSingleton instance2 = InnerClassSingleton.getInstance();
//            System.out.println(instance2);
//        }).start();

//        InnerClassSingleton instance = InnerClassSingleton.getInstance();
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("testSerial"));
//        oos.writeObject(instance);
//        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("testSerial"));
        Object object = ois.readObject();
        InnerClassSingleton o = (InnerClassSingleton) object;
        InnerClassSingleton instance = InnerClassSingleton.getInstance();
        System.out.println(instance == o);
    }

}

class InnerClassSingleton implements Serializable {
    static final long serialVersionUID = 11L;

    private static class InnerClassHolder {
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton() {
        if (InnerClassHolder.instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static InnerClassSingleton getInstance() {
        return InnerClassHolder.instance;
    }

    Object readResolve() throws ObjectStreamException {
        return InnerClassHolder.instance;
    }

    private int a = 223;
}