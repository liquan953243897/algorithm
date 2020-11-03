package com.pgz.net.netty;

/**
 * netty测试
 *
 * @author liquan_pgz@qq.com
 * @date 2020-11-03
 */
public class NettyTest {
    public static void main(String[] args) {

        //开启10条线程，每条线程就相当于一个客户端
        for (int i = 1; i <= 10; i++) {

            new Thread(new NettyClient("thread" + "--" + i)).start();
        }
    }
}
