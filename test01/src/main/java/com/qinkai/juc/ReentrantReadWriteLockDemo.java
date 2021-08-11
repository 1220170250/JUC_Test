package com.qinkai.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache =new MyCache();

        for (int i=0;i<3;i++){
            new Thread(()->{
                myCache.put(Thread.currentThread().getName(),1);

            },String.valueOf(i)).start();
        }

        for (int i=0;i<3;i++){
            new Thread(()->{
                myCache.get(Thread.currentThread().getName());
            },String.valueOf(i)).start();
        }

    }


}
class MyCache {
    //创建map 集合
    private volatile Map<String, Object> map = new HashMap<>();
    //创建读写锁对象
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    //放数据
    public void put(String key, Object value) {
        //添加写锁
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() +"准备写入:");
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(3000);
            //放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() +"写入成功:" );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            rwLock.writeLock().unlock();
        }
    }

    //取数据
    public Object get(String key) {
        //添加读锁
        rwLock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "准备读取:" );
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取成功:" +key+"："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放读锁
            rwLock.readLock().unlock();
        }
        return result;
    }
}