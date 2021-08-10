package com.qinkai.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestVolatile {
    /**
     * 交替加减
     * @param args
     */
    public static void main(String[] args) {
        DemoClass demoClass = new DemoClass();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demoClass.increment();
            }
        }, "线程A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demoClass.decrement();
            }
        }, "线程B").start();
    }
}

class DemoClass {
    //加减对象
    private int number = 0;
    /**
     * 加1
     */
    public synchronized void increment() {
        try {
            while (number != 0) {
                this.wait();
            }
            number++;
            System.out.println("--------" + Thread.currentThread().getName() + "加一成功----------,值为:" + number);
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 减一
     */
    public synchronized void decrement() {
        try {
            while (number == 0) {
                this.wait();
            }
            number--;
            System.out.println("--------" + Thread.currentThread().getName() + "减一成功----------,值为:" + number);
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Demo2Class{
    //加减对象
    private int number = 0;
    //声明锁
    private Lock lock = new ReentrantLock();
    //声明钥匙
    private Condition condition = lock.newCondition();
    /**
     * 加1
     */
    public void increment() {
        try {
            lock.lock();
            while (number != 0){
                condition.await();
            }
            number++;
            System.out.println("--------" + Thread.currentThread().getName() + "加一成功----------,值为:" + number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    /**
     * 减一
     */
    public void decrement(){
        try {
            lock.lock();
            while (number == 0){
                condition.await();
            }
            number--;
            System.out.println("--------" + Thread.currentThread().getName() + "减一成功----------,值为:" + number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


