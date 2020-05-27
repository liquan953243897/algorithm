package com.pgz.proxy;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-05-21
 */
public class Doctor implements People {
    @Override
    public String work() {
        System.out.println("悬壶济世...");
        return "治病";
    }
}
