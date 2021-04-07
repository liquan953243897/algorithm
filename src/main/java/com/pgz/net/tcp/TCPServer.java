package com.pgz.net.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-16
 */
public class TCPServer {
    public static void main(String[] args) throws Exception {
        //创建服务器端 Socket 并指明端口号
        ServerSocket welcomeSocket = new ServerSocket(6789);
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        while (true) {
            //接收客户端连接
            System.out.println("客户端建立了连接");
            Socket socket = welcomeSocket.accept();
            newCachedThreadPool.execute(() -> handler(socket));
        }
    }

    public static void handler(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[1024];

            while (true) {
                System.out.println("等待数据输入...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    String receive = new String(bytes, 0, read);
                    System.out.println(receive);
                    DataOutputStream outToClient = new DataOutputStream(outputStream);
                    outToClient.writeBytes("收到了" + receive);

                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
