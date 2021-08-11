package com.qinkai.juc.util;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    /**
     * 6 个同学陆续离开教室后值班同学才可以关门
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //定义一个数值为6 的计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //创建6 个同学
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    if (Thread.currentThread().getName().equals("同学6")) {
                        Thread.sleep(2000);
                    }
                    System.out.println(Thread.currentThread().getName() + "离开了");
                    //计数器减一,不会阻塞
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "同学" + i).start();
        }
        //主线程await 休息
        System.out.println("主线程等待");
        countDownLatch.await();
        //全部离开后自动唤醒主线程
        System.out.println("全部离开了,现在的计数器为" + countDownLatch.getCount());
    }

}
