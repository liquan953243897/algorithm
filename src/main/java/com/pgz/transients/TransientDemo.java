package com.pgz.transients;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 1、变量被transient修饰，变量将不会被序列化
 * 2、transient关键字只能修饰变量，而不能修饰方法和类。
 * 3、被static关键字修饰的变量不参与序列化，一个静态static变量不管是否被transient修饰，均不能被序列化。
 * 4、final变量值参与序列化，final transient同时修饰变量，final不会影响transient，一样不会参与序列化
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-27
 */
public class TransientDemo {

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("老王", "123");
        UserInfo.setAddr("中国");
        System.out.println("序列化之前信息：" + userInfo);

        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("userInfo.txt"));
            output.writeObject(new UserInfo("老王", "123"));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            UserInfo.setAddr("日本");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("userInfo.txt"));
            Object o = input.readObject();
            System.out.println("序列化之后信息：" + o);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
