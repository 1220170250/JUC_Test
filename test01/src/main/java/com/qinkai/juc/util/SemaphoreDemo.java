package com.qinkai.juc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SemaphoreDemo {
    /**
     * 抢车位, 10 部汽车1 个停车位
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //定义3 个停车位
        Semaphore semaphore = new Semaphore(1);
        //模拟6 辆汽车停车
        for (int i = 1; i <= 6; i++) {
            Thread.sleep(100);
            //停车
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "找车位ing");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "汽车停车成功!");
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "溜了溜了");
                    semaphore.release();
                }
            }, "汽车" + i).start();
        }
    }

}


