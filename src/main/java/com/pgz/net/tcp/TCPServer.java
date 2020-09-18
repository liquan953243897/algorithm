package com.pgz.net.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-16
 */
public class TCPServer {
    public static void main(String[] args) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //创建服务器端 Socket 并指明端口号
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) {
            //接收客户端连接
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            //获取客户端传入的字符串
            clientSentence = inFromClient.readLine();
            if (clientSentence != null)
                System.out.println(df.format(new Date()) + " from " + connectionSocket.getInetAddress() + ": " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            //向客户端发送修改后的字符串
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
