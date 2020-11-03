package com.pgz.net.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty服务端处理类
 *
 * @author liquan_pgz@qq.com
 * @date 2020-11-03
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 管理一个全局map，保存连接进服务端的通道数量
     */
    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 管理一个临时全局map，保存连接进服务端的通道数量，用作开关
     */
    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> TEMP_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 有客户端连接服务器会触发此函数
     *
     * @param ctx
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-11-03
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();

        String clientIp = inSocket.getAddress().getHostAddress();
        int clientPort = inSocket.getPort();

        //获取连接通道唯一标识
        ChannelId channelId = ctx.channel().id();

        System.out.println();
        //如果map中不包含此连接，就保存连接
        if (CHANNEL_MAP.containsKey(channelId)) {
            log.info("客户端【" + channelId + "】是连接状态，连接通道数量: " + CHANNEL_MAP.size());
        } else {
            //保存连接
            CHANNEL_MAP.put(channelId, ctx);
            TEMP_CHANNEL_MAP.put(channelId, ctx);

            log.info("客户端【" + channelId + "】连接netty服务器[IP:" + clientIp + "--->PORT:" + clientPort + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * 有客户端终止连接服务器会触发此函数
     *
     * @param ctx
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-11-03
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();

        String clientIp = inSocket.getAddress().getHostAddress();

        ChannelId channelId = ctx.channel().id();

        //包含此客户端才去删除
        if (CHANNEL_MAP.containsKey(channelId)) {
            //删除连接
            CHANNEL_MAP.remove(channelId);
            TEMP_CHANNEL_MAP.remove(channelId);
            //本地注销

            System.out.println();
            log.info("客户端【" + channelId + "】退出netty服务器[IP:" + clientIp + "--->PORT:" + inSocket.getPort() + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * 有客户端发消息会触发此函数
     *
     * @param ctx
     * @param msg
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-11-03
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println();
        log.info("加载客户端报文......");
        byte[] data = (byte[]) msg;
        log.info("【{}】:{}", ctx.channel().id(), Arrays.toString(data));

        // 下面可以解析数据，保存数据，生成返回报文，将需要返回报文写入write函数
        //响应客户端
        this.channelWrite(ctx.channel().id(), "0");
    }

    /**
     * 服务端给客户端发送消息
     *
     * @param channelId 连接通道唯一id
     * @param msg       需要发送的消息内容
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-11-03
     **/
    public void channelWrite(ChannelId channelId, Object msg) throws Exception {

        ChannelHandlerContext ctx = CHANNEL_MAP.get(channelId);

        if (ctx == null) {
            log.info("通道【" + channelId + "】不存在");
            return;
        }

        if (msg == null || msg == "") {
            log.info("服务端响应空的消息");
            return;
        }

        //将客户端的信息直接返回写入ctx
        ctx.write(msg);
        //刷新缓存区
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        String socketString = ctx.channel().remoteAddress().toString();

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * 发生异常会触发此函数
     *
     * @return
     * @author liquan_pgz@qq.com
     * date 2020-11-03
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
        ChannelId id = ctx.channel().id();
        //移除注册信息
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        CHANNEL_MAP.remove(id);
        TEMP_CHANNEL_MAP.remove(id);

        log.info(id + " 发生了错误,此连接被关闭" + "此时连通数量: " + CHANNEL_MAP.size());
        cause.printStackTrace();
    }

}
