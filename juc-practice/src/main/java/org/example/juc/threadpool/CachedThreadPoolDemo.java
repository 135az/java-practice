package org.example.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 缓存线程池示例
 * 使用场景：任务执行速度快，数量大且变化频繁，避免频繁创建和销毁线程。
 *
 * @author yjz
 */
public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 1; i <= 20; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 处理请求 " + taskId);
                try {
                    Thread.sleep(500); // 模拟任务耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
