package org.example.juc.locks;

/**
 * 死锁示例
 * 两个线程分别持有一把锁，并尝试获取对方的锁 -> 互相等待
 *
 * @author yjz
 */
public class DeadlockDemo {
    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("线程1 拿到 lockA，尝试获取 lockB...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                synchronized (lockB) {
                    System.out.println("线程1 获取到 lockB");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("线程2 拿到 lockB，尝试获取 lockA...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                synchronized (lockA) {
                    System.out.println("线程2 获取到 lockA");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
