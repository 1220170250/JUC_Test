package com.qinkai.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo {
    public static void main(String[] args) {
        PDemoClass demoClass = new PDemoClass();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                demoClass.printA(i);
            }
        }, "A 线程").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                demoClass.printB(i);
            }
        }, "B 线程").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                demoClass.printC(i);
            }
        }, "C 线程").start();
    }
}

class PDemoClass {
    //通信对象:0--打印A 1---打印B 2----打印C
    private int flag = 0;
    //声明锁
    private Lock lock = new ReentrantLock();
    //声明钥匙A
    private Condition conditionA = lock.newCondition();
    //声明钥匙B
    private Condition conditionB = lock.newCondition();
    //声明钥匙C
    private Condition conditionC = lock.newCondition();

    /**
     * A 打印5 次
     */
    public void printA(int j) {
        try {
            lock.lock();
            while (flag != 0) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出A,第" + j + "轮开始");
            //输出5 次A
            for (int i = 0; i < 5; i++) {
                System.out.println("A");
            }
            //开始打印B
            flag = 1;
            //唤醒B
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * B 打印10 次
     */
    public void printB(int j) {
        try {
            lock.lock();
            while (flag != 1) {
                conditionB.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出B,第" + j + "轮开始");
            //输出10 次B
            for (int i = 0; i < 10; i++) {
                System.out.println("B");
            }
            //开始打印C
            flag = 2;
            //唤醒C
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * C 打印15 次
     */
    public void printC(int j) {
        try {
            lock.lock();
            while (flag != 2) {
                conditionC.await();
            }
            System.out.println(Thread.currentThread().getName() + "输出C,第" + j + "轮开始");
            //输出15 次C
            for (int i = 0; i < 15; i++) {
                System.out.println("C");
            }
            System.out.println("-----------------------------------------");
            //开始打印A
            flag = 0;
            //唤醒A
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
