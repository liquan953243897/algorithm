package com.pgz.netpgm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络编程之服务demo
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-29
 */
public class DemoServer extends Thread {

    private ServerSocket serverSocket;

    private ExecutorService executor;

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        super.run();
        try {
            serverSocket = new ServerSocket(8090);
            executor = Executors.newFixedThreadPool(8);
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
//                requestHandler.start();
                //运用线程池执行客户端请求
                executor.execute(requestHandler);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        DemoServer server = new DemoServer();
        server.start();
        InetAddress localHost = InetAddress.getLocalHost();
        int port = server.getPort();
        try (Socket client = new Socket(localHost, port)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach( a ->{
                    System.out.println("服务端收到了：" + a);
            });
        }
    }
}
