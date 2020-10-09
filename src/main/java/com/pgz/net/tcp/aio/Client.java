package com.pgz.net.tcp.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * aio客户端
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-29
 */
public class Client implements Runnable {

    private AsynchronousSocketChannel asc;

    public Client() throws IOException {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect() {
        asc.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    public void write(String request) {
        try {
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //把數據读入缓冲区中
            asc.read(buffer).get();
            //切换缓冲区的读取模式
            buffer.flip();
            //构造一个字节数组接受缓冲区中的数据
            byte[] respBytes = new byte[buffer.remaining()];
            buffer.get(respBytes);
            System.out.println("客户端接收服务器端：" + new String(respBytes, StandardCharsets.UTF_8).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 简单让线程不停止。
     *
     * @auther: 李泽
     * @date: 2019/3/4 18:09
     */
    @Override
    public void run() {
        while (true) {

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client c1 = new Client();
        c1.connect();
        Client c2 = new Client();
        c2.connect();
        Client c3 = new Client();
        c3.connect();

        new Thread(c1,"c1").start();
        new Thread(c2,"c2").start();
        new Thread(c3,"c3").start();

        Thread.sleep(1000);

        c1.write("c1 aaa");
        c2.write("c2 bbhb");
        c3.write("c3 cccc");
    }
}
