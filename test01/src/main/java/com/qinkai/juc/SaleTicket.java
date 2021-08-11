package com.qinkai.juc;

import java.util.concurrent.locks.ReentrantLock;

public class SaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }

        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }

        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }

        }, "C").start();
    }


}

class Ticket {
    private int num = 30;

    public synchronized void sale() {
        if (num > 0) {
            num--;
            System.out.println(Thread.currentThread().getName() + "卖掉票,剩下：" + num);
        }
    }
}

class LTicket {
    private int num = 30;
    private final ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            if (num > 0) {
                num--;
                System.out.println(Thread.currentThread().getName() + "卖掉票,剩下：" + num);
            }
        } finally {
            lock.unlock();
        }
    }
}