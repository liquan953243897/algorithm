package com.pgz.net.rpc;

import com.pgz.net.rpc.interfaces.InterfaceDemo;
import org.junit.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 简单的饿RPC
 *
 * @author liquan_pgz@qq.com
 * @date 2020-12-18
 */
public class SimpleRpc {

    public static void export(final Object service, int port) throws Exception {
        if (service == null)
            throw new IllegalArgumentException("service instance == null");
        if (port <= 0 || port > 65535)// 端口范围0~65535
            throw new IllegalArgumentException("Invalid port " + port);
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
        ServerSocket server = new ServerSocket(port);
        while (true) {
            try {
                final Socket socket = server.accept();
                new Thread(() -> {
                    try {
                        try {
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                            try {
                                String methodName = input.readUTF();// 接口方法名
                                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();// 接口与参数类型
                                Object[] arguments = (Object[]) input.readObject();// 具体参数值
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());// 获取回传output
                                try {
                                    // 通过java的反射，动态执行实现类service的方法
                                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                                    Object result = method.invoke(service, arguments);
                                    // 将调用结果回传给调用方
                                    output.writeObject(result);
                                } catch (Throwable t) {
                                    output.writeObject(t);// 关闭
                                } finally {
                                    output.close();// 关闭
                                }
                            } finally {
                                input.close();// 关闭
                            }
                        } finally {
                            socket.close();// 关闭
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        if (interfaceClass == null)
            throw new IllegalArgumentException("Interface class == null");
        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        if (host == null || host.length() == 0)
            throw new IllegalArgumentException("Host == null!");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port " + port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, arguments) -> {
                    Socket socket = new Socket(host, port);
                    try {
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        try {
                            output.writeUTF(method.getName());
                            output.writeObject(method.getParameterTypes());
                            output.writeObject(arguments);
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                            try {
                                Object result = input.readObject();
                                if (result instanceof Throwable) {
                                    throw (Throwable) result;
                                }
                                return result;
                            } finally {
                                input.close();
                            }
                        } finally {
                            output.close();
                        }
                    } finally {
                        socket.close();
                    }
                });
    }

    @Test
    public void testServer() {
        try {
            export(new Foo(), 1111);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClient() {
        try {
            InterfaceDemo refer = refer(InterfaceDemo.class, "127.0.0.1", 1111);
            String hello_word = refer.helloWord("hello word");
            System.out.println(hello_word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
