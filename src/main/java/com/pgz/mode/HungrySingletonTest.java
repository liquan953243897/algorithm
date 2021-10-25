package com.pgz.mode;

/**
 * 通过类加载的模式实例化单例对象
 * 类加载过程：
 * 1.加在二进制数据到内存中，生成对应的class数据结构
 * 2.连接：a.验证 b.准备（给类的成员变量赋默认值）c.解析
 * 3.初始化：给类的静态变量赋初值
 * 只有真正使用对应的类时，才会初始化
 * JVM的类加载保证了单一化
 *
 * @author liquan_pgz@qq.com
 * @date 2021/9/5 5:33 下午
 */
public class HungrySingletonTest {
    public static void main(String[] args) {
        HungrySingleton singleton = HungrySingleton.getInstance();
        HungrySingleton instance = HungrySingleton.getInstance();
        System.out.println(singleton == instance);
    }
}

class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        if (instance != null) {
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}