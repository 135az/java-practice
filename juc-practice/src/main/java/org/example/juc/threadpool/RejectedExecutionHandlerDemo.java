package org.example.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示 RejectedExecutionHandler 的使用
 * <p>
 * 线程池任务太多时，会触发拒绝策略。
 * JDK 内置的几种拒绝策略：
 * 1. AbortPolicy（默认）：抛出 RejectedExecutionException
 * 2. CallerRunsPolicy：调用者线程执行任务
 * 3. DiscardPolicy：直接丢弃任务，不报错
 * 4. DiscardOldestPolicy：丢弃队列中最旧的任务，尝试提交新任务
 */
public class RejectedExecutionHandlerDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                3, // 最大线程数
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), // 队列容量只有 2
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // 使用 CallerRunsPolicy 拒绝策略
        );

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                try {
                    Thread.sleep(2000); // 模拟任务耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
