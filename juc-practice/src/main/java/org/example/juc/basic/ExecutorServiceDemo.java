package org.example.juc.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池 ExecutorService 管理线程
 * 好处：避免频繁创建/销毁线程，提高性能
 *
 * @author yjz
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        // 创建一个固定大小的线程池（常用）
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("线程 " + Thread.currentThread().getName() + " 执行任务 " + taskId);
            });
        }

        // 关闭线程池（不再接收新任务，执行完已提交的任务后关闭）
        executor.shutdown();
    }
}
