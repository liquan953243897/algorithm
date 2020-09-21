package com.pgz.net.udp.nio;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 非阻UDP塞服务端
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-20
 */
public class UdpNioServer {

    private static final Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {
        //创建channel
        DatagramChannel channel = DatagramChannel.open();
        //指定为非阻塞方式
        channel.configureBlocking(false);
        DatagramSocket socket = channel.socket();
        //绑定ip和端口
        InetSocketAddress address = new InetSocketAddress(7001);
        socket.bind(address);

        //创建监听器
        Selector selector = Selector.open();
        //注册读事件
        channel.register(selector, SelectionKey.OP_READ);

        //记录前一客户端地址，用于新起发送线程，仅示例，实际中用map等方式标记
        String preClientAddress = "";

        //读缓冲
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            //等事件出现
            if (selector.select() < 1) {
                continue;
            }

            //获取发生的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                //获取事件，移除正在处理的事件
                SelectionKey key = it.next();
                it.remove();

                //读取消息
                if (key.isReadable()) {
                    DatagramChannel datagramChannel = (DatagramChannel) key.channel();
                    readBuffer.clear();
                    SocketAddress sa = datagramChannel.receive(readBuffer);
                    //新建发送消息线程
                    if (!preClientAddress.equals(sa.toString())) {
                        new WriteThread(channel, sa).start();
                        preClientAddress = sa.toString();
                    }
                    readBuffer.flip();
                    String msg = charset.decode(readBuffer).toString();
                    System.out.println("server receive msg : " + msg);

                }
            }
        }
    }


    /**
     * 发送消息线程
     */
    public static class WriteThread extends Thread {
        //channel
        private DatagramChannel channel;
        //客户端地址
        private SocketAddress socketAddress;

        public WriteThread(DatagramChannel channel, SocketAddress socketAddress) {
            this.channel = channel;
            this.socketAddress = socketAddress;
        }

        @Override
        public void run() {
            try {
                //写缓冲
                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("server send msg:");
                    String msg = scanner.nextLine();

                    //消息写入缓冲区，并发送消息
                    writeBuffer.put(msg.getBytes());
                    writeBuffer.flip();
                    channel.send(writeBuffer, socketAddress);

                    //发送完后，清空缓冲区
                    writeBuffer.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
