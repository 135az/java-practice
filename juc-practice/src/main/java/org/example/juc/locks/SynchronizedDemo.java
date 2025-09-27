package org.example.juc.locks;

/**
 * 演示 synchronized 内置锁
 * 特点：
 * - 锁的是对象（实例方法锁 this，静态方法锁 Class）
 * - 保证同一时间只有一个线程能进入同步代码块
 *
 * @author yjz
 */
public class SynchronizedDemo {
    private int count = 0;

    public synchronized void increment() {
        // 同步方法，相当于给 this 加锁
        count++;
    }

    public void syncBlock() {
        // 同步代码块，可以指定锁对象
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo demo = new SynchronizedDemo();

        // 启动多个线程并发执行
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demo.syncBlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("最终 count 值: " + demo.getCount());
    }
}
