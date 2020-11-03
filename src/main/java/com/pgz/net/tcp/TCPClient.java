package com.pgz.net.tcp;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-16
 */
public class TCPClient {


//    @Test
    public static void main1() throws Exception {
        String sentence;
        String modifiedSentence;
        System.out.println("请输入一个英文字符串，服务器将返回其大写形式（输入exit退出）");
        //创建客户端 Socket 并指明需要连接的服务器端的主机名及端口号，即用即时创建连接
        Socket clientSocket = null;
        DataOutputStream outToServer = null;
        while (true) {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            sentence = inFromUser.readLine();
            if ("conn".equalsIgnoreCase(sentence)) {
                clientSocket = new Socket("localhost", 6789);
                outToServer = new DataOutputStream(clientSocket.getOutputStream());
                clientSocket.setKeepAlive(true);
                continue;
            }
//            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if (sentence.equals("exit")) break;
            //向服务器发送数据
            if (outToServer != null) {
                outToServer.writeBytes(sentence + "\n");
                System.out.println("写入了数据===" + sentence);
            }
            //接收服务器返回数据
//            modifiedSentence = inFromServer.readLine();
//            System.out.println("FROM SERVER: " + modifiedSentence);
            if ("close".equalsIgnoreCase(sentence) && clientSocket != null) {
                clientSocket.close();
            }
        }
    }

    public static void main(String[] a) throws Exception {
//        for (int i1 = 0; i1 < 10; i1++) {
//            new Thread(new RunnableImp()).start();
//        }
        main1();
    }

    private static class RunnableImp implements Runnable {

        @Override
        public void run() {
            System.out.println("请输入一个英文字符串，服务器将返回其大写形式（输入exit退出）");
            for (int i = 0; i < 30; i++) {
                try {
                    //创建客户端 Socket 并指明需要连接的服务器端的主机名及端口号，即用即时创建连接
                    Socket clientSocket = new Socket("localhost", 6789);
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //向服务器发送数据
                    outToServer.writeBytes(Thread.currentThread().getName() + "序号" + i + '\n');
                    //接收服务器返回数据
                    String modifiedSentence;
                    modifiedSentence = inFromServer.readLine();
                    System.out.println("FROM SERVER: " + modifiedSentence);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
