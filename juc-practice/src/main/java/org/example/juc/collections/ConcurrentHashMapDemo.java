package org.example.juc.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentHashMap 示例
 * 应用场景：高并发缓存，支持读多写多。
 *
 * @author yjz
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> stock = new ConcurrentHashMap<>();

        // 初始化库存
        stock.put("iPhone", 10);
        stock.put("MacBook", 5);

        ExecutorService pool = Executors.newFixedThreadPool(5);

        // 模拟用户下单（多线程修改库存）
        Runnable buyerTask = () -> {
            for (int i = 0; i < 3; i++) {
                stock.computeIfPresent("iPhone", (k, v) -> v > 0 ? v - 1 : v);
                System.out.println(Thread.currentThread().getName() + " 购买了一台 iPhone, 剩余: " + stock.get("iPhone"));
            }
        };

        for (int i = 0; i < 5; i++) {
            pool.execute(buyerTask);
        }

        pool.shutdown();
    }
}
