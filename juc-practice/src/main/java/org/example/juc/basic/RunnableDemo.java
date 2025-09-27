package org.example.juc.basic;

/**
 * 使用 Runnable 接口创建线程
 * 优点：避免单继承限制，更加灵活
 *
 * @author yjz
 */
public class RunnableDemo {
    public static void main(String[] args) {
        // 方式一：匿名内部类
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable 线程执行");
            }
        });

        // 方式二：Lambda 表达式（推荐）
        Thread t2 = new Thread(() -> System.out.println("Lambda 线程执行"));

        t1.start();
        t2.start();
    }
}
