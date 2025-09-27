package org.example.juc.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue 示例
 * 应用场景：生产者-消费者，如日志处理系统。
 *
 * @author yjz
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> logQueue = new ArrayBlockingQueue<>(5);

        // 生产者线程：写日志
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    logQueue.put("Log-" + i);
                    System.out.println("📥 生成日志：" + "Log-" + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // 消费者线程：处理日志
        Runnable consumer = () -> {
            try {
                while (true) {
                    String log = logQueue.take();
                    System.out.println("📤 处理日志：" + log);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
