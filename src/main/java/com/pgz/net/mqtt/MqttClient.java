package com.pgz.net.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-18
 */
public class MqttClient {

    private MqttClient client;
    private MqttConnectOptions options;
    private static final String[] myTopics = {"Topics/htjs/phoneToServer", "Topics/htjs/serverToPhone"};
    private static final int[] myQos = {2, 2};

    public static void main(String[] args) {
        System.out.println("client start...");
        MyMqtt myMqtt = new MyMqtt("client");
        myMqtt.subscribe(myTopics, myQos);
    }
}
