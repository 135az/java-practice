package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示 AtomicInteger 的常用方法
 * 适用于计数器、并发统计等场景
 *
 * @author yjz
 */
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInt = new AtomicInteger(0);

        // 获取和设置
        System.out.println("初始值：" + atomicInt.get());
        atomicInt.set(5);
        System.out.println("设置后的值：" + atomicInt.get());

        // 自增自减
        System.out.println("getAndIncrement()：" + atomicInt.getAndIncrement()); // 先返回再+1
        System.out.println("incrementAndGet()：" + atomicInt.incrementAndGet()); // 先+1再返回

        // CAS 操作
        boolean success = atomicInt.compareAndSet(7, 10); // 如果当前值 == 7，则更新为10
        System.out.println("CAS 更新结果：" + success + "，当前值：" + atomicInt.get());

        // 加指定值
        System.out.println("addAndGet(5)：" + atomicInt.addAndGet(5)); // 当前值 + 5
    }
}
