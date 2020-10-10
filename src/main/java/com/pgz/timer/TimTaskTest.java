package com.pgz.timer;

import java.util.TimerTask;

/**
 * 定时任务
 *
 * @author liquan_pgz@qq.com
 * @date 2020-10-09
 */
public class TimTaskTest extends TimerTask {

    @Override
    public void run() {
        System.out.println("我在执行定时任务...");
    }
}
