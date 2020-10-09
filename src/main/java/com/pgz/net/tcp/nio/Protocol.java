package com.pgz.net.tcp.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 协议接口
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-29
 */
public interface Protocol {

    //accept I/O形式
    void handleAccept(SelectionKey key) throws IOException;

    //read I/O形式
    void handleRead(SelectionKey key) throws IOException;

    //write I/O形式
    void handleWrite(SelectionKey key) throws IOException;
}
