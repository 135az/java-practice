package org.example.juc.synchronizers;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 使用场景：
 * 控制同时访问某个资源的线程数量。
 *
 * @author yjz
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 停车位数量
        int permits = 3;
        Semaphore semaphore = new Semaphore(permits);

        for (int i = 1; i <= 6; i++) {
            new Thread(new Car(semaphore, i)).start();
        }
    }

    static class Car implements Runnable {
        private final Semaphore semaphore;
        private final int carId;

        Car(Semaphore semaphore, int carId) {
            this.semaphore = semaphore;
            this.carId = carId;
        }

        @Override
        public void run() {
            try {
                System.out.println("🚗 车辆 " + carId + " 正在等待进入停车场...");
                semaphore.acquire(); // 获取车位
                System.out.println("✅ 车辆 " + carId + " 已停入车位");
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println("🚙 车辆 " + carId + " 离开车位");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // 释放车位
            }
        }
    }
}
