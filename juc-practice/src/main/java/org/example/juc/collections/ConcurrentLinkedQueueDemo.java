package org.example.juc.collections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue 示例
 * 应用场景：高并发非阻塞队列，如任务调度。
 *
 * @author yjz
 */
public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) {
        Queue<String> tasks = new ConcurrentLinkedQueue<>();

        // 多线程添加任务
        Runnable producer = () -> {
            for (int i = 1; i <= 5; i++) {
                tasks.offer(Thread.currentThread().getName() + "-Task" + i);
            }
        };

        new Thread(producer, "Producer1").start();
        new Thread(producer, "Producer2").start();

        // 消费任务
        Runnable consumer = () -> {
            while (true) {
                String task = tasks.poll();
                if (task != null) {
                    System.out.println(Thread.currentThread().getName() + " 处理 " + task);
                }
            }
        };

        new Thread(consumer, "Consumer1").start();
    }
}
