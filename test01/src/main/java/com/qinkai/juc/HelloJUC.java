package com.qinkai.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class HelloJUC {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":aa");
        }, "a").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":bb");
        }, "b").start();

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            return "1024";
        });
        new Thread(futureTask, "AA").start();
        System.out.println(futureTask.get());
    }


}


