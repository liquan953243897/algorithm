package com.pgz.net.netty;

import com.pgz.net.netty.initializer.NettyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * netty服务启动类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-11-03
 */
@Slf4j
@Component
public class NettyServer {

    @Autowired
    private NettyServerChannelInitializer initializer;

    public void start(Integer port) {
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            log.info("netty服务器准备监听端口：" + port);
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)  // 绑定线程池
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    //编码、解码、处理器
                    .childHandler(initializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
                    //保持长连接，2小时无数据激活心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("netty服务器开始监听端口：" + port);
            //关闭channel和块，直到它被关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
