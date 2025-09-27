package org.example.juc.basic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务：ScheduledExecutorService
 * 类似于 Timer，但更强大、支持线程池
 *
 * @author yjz
 */
public class ScheduledExecutorDemo {
    public static void main(String[] args) {
        // 创建一个单线程定时任务调度器
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 延迟 2 秒后执行一次
        scheduler.schedule(() -> System.out.println("延迟执行任务"), 2, TimeUnit.SECONDS);

        // 每隔 1 秒执行一次（固定速率）
        scheduler.scheduleAtFixedRate(
                () -> System.out.println("周期任务：" + System.currentTimeMillis()),
                1, 1, TimeUnit.SECONDS);
    }
}
