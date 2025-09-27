package org.example.juc.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 ReentrantLock（可重入锁）
 * 优点：
 * - 灵活：可以尝试获取锁、可中断、公平锁
 * - 必须手动释放锁
 *
 * @author yjz
 */
public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock(); // 加锁
        try {
            count++;
        } finally {
            lock.unlock(); // 确保释放锁
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo demo = new ReentrantLockDemo();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                demo.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("最终 count 值: " + demo.count);
    }
}
