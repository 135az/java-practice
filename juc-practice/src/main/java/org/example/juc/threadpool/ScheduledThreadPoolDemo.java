package org.example.juc.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程池示例
 * 使用场景：定时任务，如心跳检测、定期清理缓存
 *
 * @author yjz
 */
public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // 延迟 2 秒执行
        scheduler.schedule(() ->
                        System.out.println("延迟任务执行: " + Thread.currentThread().getName()),
                2, TimeUnit.SECONDS);

        // 每隔 3 秒执行一次
        scheduler.scheduleAtFixedRate(() ->
                        System.out.println("周期任务执行: " + Thread.currentThread().getName()),
                1, 3, TimeUnit.SECONDS);

        // 模拟 10 秒后关闭
        scheduler.schedule(() -> {
            System.out.println("关闭线程池...");
            scheduler.shutdown();
        }, 10, TimeUnit.SECONDS);
    }
}
