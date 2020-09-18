package com.pgz.net.mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-18
 */
public class MqttSever {

    public static void main(String[] args) throws IOException {
        MyMqtt myMqtt = new MyMqtt("Sever");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String sentence = inFromUser.readLine();
            if (sentence.equals("exit")) break;
            myMqtt.sendMessage("发你信息");
        }
    }

}
