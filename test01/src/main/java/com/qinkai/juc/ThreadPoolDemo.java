package com.qinkai.juc;

import java.util.concurrent.*;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //ExecutorService threadPool= Executors.newCachedThreadPool();
        ExecutorService threadPool= new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i <10 ; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"  办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }


    }
}
