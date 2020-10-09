package com.pgz.net.tcp.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * aio服务端
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-29
 */
public class Server {

    //线程池
    private ExecutorService executorService;
    //AIO工作的线程组
    private AsynchronousChannelGroup threadGroup;
    //服务器通道
    public AsynchronousServerSocketChannel assc;

    public Server(int port) {
        try {
            //创建一个可伸缩的线程池
            executorService = Executors.newCachedThreadPool();
            //创建干活的线程组，负责连接上之前的所有的琐碎的工作。
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //创建服务器通道,并且设置为这个通道干活的线程组
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //绑定端口
            assc.bind(new InetSocketAddress(port));
            System.out.println("server start!port:" + port);
            //进行阻塞，实际上并没有卡在这。
            assc.accept(this, new ServerCompletionHandler());
            //阻塞在这不让服务停止，因为accept不会阻塞。
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8000);
    }

}
