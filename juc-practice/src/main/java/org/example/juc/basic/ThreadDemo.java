package org.example.juc.basic;

/**
 * 演示最基本的线程创建方式：继承 Thread 类
 * 适合简单演示，但在实际开发中不推荐（因为 Java 不支持多继承）
 *
 * @author yjz
 */
public class ThreadDemo {
    public static void main(String[] args) {
        // 创建并启动线程
        MyThread t1 = new MyThread();
        t1.start();

        // 主线程任务
        for (int i = 0; i < 5; i++) {
            System.out.println("主线程执行：" + i);
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        // 子线程任务
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程执行：" + i);
        }
    }
}
