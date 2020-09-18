package com.pgz.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-16
 */
public class UDPServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] sendData;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            byte[] receiveData = new byte[1024];
            //创建接收数据报包
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            //接收客户端数据报包
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            //获取客户端地址
            InetAddress IPAddress = receivePacket.getAddress();
            System.out.println(df.format(new Date()) + " from " + IPAddress + ": " + sentence);
            //获得客户端端口号
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
            //向客户端发送数据报包
            serverSocket.send(sendPacket);
        }
    }
}
