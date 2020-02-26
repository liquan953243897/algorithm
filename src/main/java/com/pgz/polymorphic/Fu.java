package com.pgz.polymorphic;

/**
 * 父
 *
 * @author liquan_pgz@qq.com
 * @date 2020-02-26
 */
public class Fu extends Ye{

    public String show(Fu obj) {
        return ("Fu and Fu");
    }

    public String show(Ye obj) {
        return ("Fu and Ye");
    }

    public void show() {

    }

}
