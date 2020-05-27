package com.pgz.proxy;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-05-14
 */
public class Teacher implements People, Father {

    @Override
    public String work() {
        System.out.println("老师教书育人...");
        return "教书";
    }

    @Override
    public String careFor(String kid) {
        System.out.println("照料>>>>>" + kid);
        return "照顾孩子";
    }
}
