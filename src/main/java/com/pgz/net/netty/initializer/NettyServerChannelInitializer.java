package com.pgz.net.netty.initializer;

import com.github.halosee.receive.server.handler.NettyServerHandler;
import com.github.halosee.receive.service.DataReceiveService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务端初始化，客户端与服务器端连接一旦创建，这个类中方法就会被回调，设置出站编码器和入站解码器
 *
 * @author liquan_pgz@qq.com
 * @date 2020-11-03
 */
@Component
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 注入数据接收业务方法
     */
    @Autowired
    private DataReceiveService receiveService;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast("decoder", new ByteArrayDecoder());
        channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast(new NettyServerHandler(receiveService));
    }
}
