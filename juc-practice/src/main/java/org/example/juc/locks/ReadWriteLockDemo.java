package org.example.juc.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示 ReentrantReadWriteLock
 * 特点：
 * - 读锁共享（多个线程可同时读）
 * - 写锁独占（写操作互斥）
 *
 * @author yjz
 */
public class ReadWriteLockDemo {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int value = 0;

    public void write(int newValue) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入: " + newValue);
            value = newValue;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 读取: " + value);
            return value;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        // 启动多个读线程
        for (int i = 0; i < 3; i++) {
            new Thread(demo::read, "读线程-" + i).start();
        }

        // 启动写线程
        new Thread(() -> demo.write(42), "写线程").start();
    }
}
