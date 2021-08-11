package com.qinkai.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //运行一个没有返回值的异步任务
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("子线程启动干活");
                Thread.sleep(5000);
                System.out.println("子线程完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //主线程阻塞
        future.get();
        System.out.println("主线程结束");

        //运行一个有返回值的异步任务
        CompletableFuture<String> future02 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("子线程开始任务");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "子线程完成了!";
        });


        //主线程阻塞
         future02.whenComplete((t,u)->{
             //得到异步任务返回值t
             System.out.println("异步任务返回值:"+ t);
         }).exceptionally(f ->{
             //异步任务有异常执行
             return "4444";
         }).get();
        System.out.println("主线程结束" );


    }
}
