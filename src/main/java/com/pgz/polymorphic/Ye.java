package com.pgz.polymorphic;

/**
 * 爷
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-26
 */
public class Ye {

    public String show(Sun obj) {
        return ("Ye and Sun");
    }

    public String show(Ye obj) {
        return ("Ye and Ye");
    }

}
