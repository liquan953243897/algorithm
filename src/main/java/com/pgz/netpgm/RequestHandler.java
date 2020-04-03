package com.pgz.netpgm;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 请求处理器
 *
 * @author liquan_pgz@qq.com
 * @date 2020-03-29
 */
public class RequestHandler extends Thread {
    private Socket socket;
    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println("Hello world!");
            out.flush();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                System.out.println("客户端说：" + line);
                out.println(line);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
