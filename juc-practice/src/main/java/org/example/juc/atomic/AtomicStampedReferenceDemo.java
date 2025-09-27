package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 演示 AtomicStampedReference
 * 解决 CAS 中的 ABA 问题
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        String initialRef = "A";
        AtomicStampedReference<String> atomicStampedRef =
                new AtomicStampedReference<>(initialRef, 0);

        System.out.println("初始值：" + atomicStampedRef.getReference() + "，版本：" + atomicStampedRef.getStamp());

        // 模拟线程1修改 A -> B
        boolean success1 = atomicStampedRef.compareAndSet("A", "B", 0, 1);
        System.out.println("线程1 CAS：" + success1 + "，值：" + atomicStampedRef.getReference() + "，版本：" + atomicStampedRef.getStamp());

        // 模拟线程2尝试 A -> C，版本不一致导致失败
        boolean success2 = atomicStampedRef.compareAndSet("A", "C", 0, 1);
        System.out.println("线程2 CAS：" + success2 + "，值：" + atomicStampedRef.getReference() + "，版本：" + atomicStampedRef.getStamp());
    }
}
