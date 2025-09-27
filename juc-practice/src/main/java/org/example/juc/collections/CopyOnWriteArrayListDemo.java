package org.example.juc.collections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 示例
 * 应用场景：读多写少，如订阅通知系统。
 *
 * @author yjz
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        List<String> subscribers = new CopyOnWriteArrayList<>();

        // 多线程添加订阅者
        new Thread(() -> {
            subscribers.add("UserA");
            subscribers.add("UserB");
        }).start();

        new Thread(() -> {
            subscribers.add("UserC");
        }).start();

        // 多线程读取订阅者列表
        new Thread(() -> {
            for (String user : subscribers) {
                System.out.println("发送通知给: " + user);
            }
        }).start();
    }
}
