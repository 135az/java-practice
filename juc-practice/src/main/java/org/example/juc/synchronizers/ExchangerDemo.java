package org.example.juc.synchronizers;

import java.util.concurrent.Exchanger;

/**
 * Exchanger 使用场景：
 * 两个线程之间的数据交换。
 *
 * @author yjz
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String myData = "订单数据";
                System.out.println("生产者准备数据：" + myData);
                String result = exchanger.exchange(myData); // 交换数据
                System.out.println("生产者收到反馈：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者").start();

        new Thread(() -> {
            try {
                String myData = "处理结果";
                System.out.println("消费者准备数据：" + myData);
                String result = exchanger.exchange(myData); // 交换数据
                System.out.println("消费者收到数据：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "消费者").start();
    }
}
