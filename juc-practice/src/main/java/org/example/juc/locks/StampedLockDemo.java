package org.example.juc.locks;

import java.util.concurrent.locks.StampedLock;

/**
 * 演示 StampedLock
 * 特点：
 * - 支持乐观读锁（减少竞争，提高并发性能）
 * - 需要检查锁状态是否有效
 *
 * @author yjz
 */
public class StampedLockDemo {
    private final StampedLock lock = new StampedLock();
    private int value = 0;

    public void write(int newValue) {
        long stamp = lock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入: " + newValue);
            value = newValue;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int read() {
        long stamp = lock.tryOptimisticRead(); // 乐观读锁
        int currentValue = value;

        // 校验数据是否被修改过
        if (!lock.validate(stamp)) {
            // 乐观锁失败，升级为悲观读锁
            stamp = lock.readLock();
            try {
                currentValue = value;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return currentValue;
    }

    public static void main(String[] args) {
        StampedLockDemo demo = new StampedLockDemo();

        new Thread(() -> demo.write(100), "写线程").start();
        new Thread(() -> System.out.println("读取值: " + demo.read()), "读线程").start();
    }
}
