package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用 AtomicLong 实现全局唯一 ID 生成器
 *
 * @author yjz
 */
public class GlobalIdGenerator {
    // 初始ID
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1000);

    /**
     * 获取全局唯一 ID
     */
    public static long getNextId() {
        // 保证多线程下不重复
        return ID_GENERATOR.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟多个线程同时生成 ID
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 生成ID：" + getNextId());
            }
        };

        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
