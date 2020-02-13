package com.pgz.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * //TODO 添加类功能描述
 *
 * @author: liquan_pgz@qq.com
 * @date: 2018-09-29 14:19
 * @Version: 1.0
 */
public class MyLoadBalance {

    public static void main(String[] args) {

        List<String> ips = new ArrayList<>();

        ips.add("192.168.0.1");

        ips.add("192.168.0.2");

        ips.add("192.168.0.3");

        IntStream.range(0, 10).forEach(i -> {
            System.out.println("1选择ip:" + doSelect(ips));
        });

        IntStream.range(0, 10).forEach(i -> {
            System.out.println("2选择ip:" + doSelect2(ips));
        });
    }

    private static Integer index = 0;

    /**
     * 加锁同步实现线程安全的轮询负载均衡算法
     *
     * @param ipList
     * @return
     * @author liquan_pgz@qq.com
     * date 2018/9/29
     **/
    public static String doSelect(List<String> ipList) {
        synchronized (index) {
            if (index >= ipList.size()) {
                index = 0;
            }
            String ip = ipList.get(index);
            index++;
            return ip;
        }
    }

    private static AtomicInteger index_ = new AtomicInteger(0);

    /**
     * 原子类实现线程安全的轮询负载均衡算法
     *
     * @param ipList
     * @return
     * @author liquan_pgz@qq.com
     * date 2018/9/29
     **/
    public static String doSelect2(List<String> ipList) {

        if (index_.get() >= ipList.size()) {
            index_ = new AtomicInteger(0);
        }
        String ip = ipList.get(index_.get());
        index_.incrementAndGet();
        return ip;
    }
}
