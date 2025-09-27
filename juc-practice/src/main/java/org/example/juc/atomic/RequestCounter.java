package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用 AtomicInteger 实现高并发请求计数器
 *
 * @author yjz
 */
public class RequestCounter {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    /**
     * 记录一次请求
     */
    public static void recordRequest() {
        COUNTER.incrementAndGet();
    }

    /**
     * 获取总请求数
     */
    public static int getRequestCount() {
        return COUNTER.get();
    }

    public static void main(String[] args) throws InterruptedException {
        // 模拟1000个请求并发进入
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                recordRequest();
            }
        };
        // 10个线程，每个100次请求
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("总请求数：" + getRequestCount());
    }
}
