package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 演示 AtomicLong 的常用方法
 * 常用于大数值的并发计数，如全局ID生成、请求统计
 *
 * @author yjz
 */
public class AtomicLongDemo {
    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(100L);

        System.out.println("初始值：" + atomicLong.get());

        // 累加
        atomicLong.addAndGet(50);
        System.out.println("加50后：" + atomicLong.get());

        // CAS
        boolean success = atomicLong.compareAndSet(150, 200);
        System.out.println("CAS 结果：" + success + "，当前值：" + atomicLong.get());
    }
}
