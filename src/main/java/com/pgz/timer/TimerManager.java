package com.pgz.timer;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;

/**
 * 定时任务管理器
 *
 * @author liquan_pgz@qq.com
 * @date 2020-10-09
 */
public class TimerManager {

    /**
     * 单例模式
     */
    private static TimerManager timerManager = null;

    private TimerManager() {
    }

    public static TimerManager getInstance() {
        if (timerManager == null) {
            timerManager = new TimerManager();
        }
        return timerManager;
    }

    /**
     * 定时器
     */
    private Timer timer = new Timer("homePageTimer");

    /**
     * 定时任务
     */
    private TimTaskTest timerTask = null;

    /**
     * 启动定时任务
     */
    public void startTimerTask() {
        timer.purge();
        if (timerTask == null) {
            timerTask = new TimTaskTest();
        }
        timer.schedule(timerTask, new Date(), 5000);
    }

    /**
     * 定时任务取消
     */
    public void stopTimerTask() {
        System.out.println("取消任务");
        timerTask.cancel();
        timerTask = null;//如果不重新new，会报异常
    }

    public static void main(String[] args) {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        loop:
        while (true) {
            try {
                String cmd = inFromUser.readLine();
                System.out.println("输入命令为：" + cmd);
                switch (cmd) {
                    case "start":
                        getInstance().startTimerTask();
                        break;
                    case "stop":
                        getInstance().stopTimerTask();
                        break;
                    case "exit":
                        break loop;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
