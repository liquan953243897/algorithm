package com.pgz.net.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-16
 */
public class UDPClient {
    public static void main(String[] args) {
        try {
            int port = new Random().nextInt(1000);
            System.out.println("port===" + port);
            DatagramSocket clientSocket = new DatagramSocket(port);
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            //获取本地 IP 地址
            InetAddress IPAddress = InetAddress.getLocalHost();
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            System.out.println("请输入一句英文，服务器会返回其大写形式（输入exit退出）");
            while (true) {
                String sentence = inFromUser.readLine();
                if (sentence.equals("exit")) break;
                sendData = sentence.getBytes();
                //创建发送数据报包，并标注源地址#，目的地址#
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                //发送数据报包
                clientSocket.send(sendPacket);
                //创建接收数据报包
//                DatagramPacket receivePacket =
//                        new DatagramPacket(receiveData, receiveData.length);
//                //接收服务器的数据报包
//                clientSocket.receive(receivePacket);
//                String modifiedSentence = new String(receivePacket.getData());
//                System.out.println("FROM SERVER: " + modifiedSentence);
            }
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
