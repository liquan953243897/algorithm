package com.pgz.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 通过反射越过泛型检查
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-26
 */
public class Demo {

    public static void main(String[] args) throws Exception{
        ArrayList<String> strList = new ArrayList<>();
        strList.add("aaa");
        strList.add("bbb");
//        strList.add(100);

        //	strList.add(100);
        //获取ArrayList的Class对象，反向的调用add()方法，添加数据
        Class listClass = strList.getClass(); //得到 strList 对象的字节码 对象
        //获取add()方法
        Method m = listClass.getMethod("add", Object.class);
        //调用add()方法
        m.invoke(strList, 100);

        //遍历集合
        for(Object obj : strList){
            System.out.println(obj);
        }
    }

}
