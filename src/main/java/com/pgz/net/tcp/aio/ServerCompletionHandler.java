package com.pgz.net.tcp.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 服务端业务处理handler实现
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-29
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        //这个对象被使用了，accept方法被执行过了，如果不设置一个本类对象去执行任务的话，不重新监听的话，新的请求
        // 绝对进不来，所以要调用一下 server中 这句话的等效语句assc.accept(this,new ServerCompletionHandler());
        //类似递归，让这个类重新处于监听状态，处理下一个请求，没有new新对象。各个对象之间互不干扰。
        attachment.assc.accept(attachment, this);
        //读取数据
        read(result);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }

    private void read(AsynchronousSocketChannel asc) {
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //拿出通道来执行读取的业务
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //进行读取之前重新设置pos和limit
                attachment.flip();
                //打印获得的字节数
                System.out.println("resultSize = " + result);
                //获取读取的数据
                String body = new String(attachment.array()).trim();
                System.out.println("server accept body = " + body);
                String resp = "服务器收到你发来的数据：" + body;
                write(asc, resp);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String resp) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(resp.getBytes());
            buffer.flip();
            //get写不写都行
            asc.write(buffer).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
