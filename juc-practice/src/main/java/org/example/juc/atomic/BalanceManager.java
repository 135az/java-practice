package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 使用 AtomicStampedReference 解决 ABA 问题
 *
 * @author yjz
 */
public class BalanceManager {

    // 初始余额100，版本号0
    private static final AtomicStampedReference<Integer> BALANCE =
            new AtomicStampedReference<>(100, 0);

    /**
     * 扣款操作
     */
    public static boolean withdraw(int amount) {
        while (true) {
            int stamp = BALANCE.getStamp();
            int currentBalance = BALANCE.getReference();

            if (currentBalance < amount) {
                System.out.println("余额不足，扣款失败");
                return false;
            }

            int newBalance = currentBalance - amount;
            if (BALANCE.compareAndSet(currentBalance, newBalance, stamp, stamp + 1)) {
                System.out.println(Thread.currentThread().getName() +
                        " 扣款 " + amount + " 成功，新余额：" + newBalance + "，版本：" + (stamp + 1));
                return true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> withdraw(30);

        Thread t1 = new Thread(task, "用户1");
        Thread t2 = new Thread(task, "用户2");
        Thread t3 = new Thread(task, "用户3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("最终余额：" + BALANCE.getReference() + "，版本：" + BALANCE.getStamp());
    }
}
