package org.example.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定大小线程池示例
 * 使用场景：任务数量较大，但线程数可控，避免系统因过多线程而崩溃。
 *
 * @author yjz
 */
public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        // 创建一个固定大小为 3 的线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 正在执行任务 " + taskId);
                try {
                    Thread.sleep(2000); // 模拟任务耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " 完成任务 " + taskId);
            });
        }

        executor.shutdown(); // 关闭线程池
    }
}
