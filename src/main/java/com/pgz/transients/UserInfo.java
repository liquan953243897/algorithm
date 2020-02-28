package com.pgz.transients;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-27
 */
public class UserInfo implements Serializable {

    private String name;

    private static String addr;

    private transient String password;

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static void setAddr(String addr) {
        UserInfo.addr = addr;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
