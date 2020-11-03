package com.pgz.net.netty;

import com.pgz.net.netty.handler.NettyClientHandler;
import com.pgz.net.netty.initializer.NettyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Netty客户端
 *
 * @author liquan_pgz@qq.com
 * @date 2020-11-03
 */
@Slf4j
@Data
public class NettyClient implements Runnable {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8888"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    private String content;

    public NettyClient(String content) {
        this.content = content;
    }

    @Override
    public void run() {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            int num = 0;
            boolean boo = true;

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyClientChannelInitializer() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(HOST, PORT).sync();

            while (boo) {

                num++;

                future.channel().writeAndFlush(content + "--" + new Date());

                try { //休眠一段时间
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //每一条线程向服务端发送的次数
                if (num == 100) {
                    boo = false;
                }
            }

            log.info(content + "-----------------------------" + num);
            //future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
