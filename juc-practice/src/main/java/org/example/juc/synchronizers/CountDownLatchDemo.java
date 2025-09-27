package org.example.juc.synchronizers;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 使用场景：
 * 等待一组任务完成后再继续执行。
 *
 * @author yjz
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int serviceCount = 3;
        CountDownLatch latch = new CountDownLatch(serviceCount);

        // 模拟三个微服务的启动
        Runnable serviceTask = () -> {
            String serviceName = Thread.currentThread().getName();
            try {
                System.out.println(serviceName + " 正在启动...");
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(serviceName + " 启动完成！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // 启动完成，计数器减一
            }
        };

        new Thread(serviceTask, "数据库服务").start();
        new Thread(serviceTask, "缓存服务").start();
        new Thread(serviceTask, "消息队列服务").start();

        System.out.println("主线程等待所有服务启动...");
        latch.await(); // 阻塞，直到所有服务启动完
        System.out.println("所有服务已启动，系统对外提供服务！");
    }
}
