package org.example.juc.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 使用场景：
 * 多个线程需要在某个集合点等待，统一出发。
 *
 * @author yjz
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        int playerCount = 4;

        CyclicBarrier barrier = new CyclicBarrier(playerCount, () -> {
            System.out.println("所有玩家已到齐，游戏开始！");
        });

        for (int i = 1; i <= playerCount; i++) {
            new Thread(new Player(barrier, i)).start();
        }
    }

    static class Player implements Runnable {
        private final CyclicBarrier barrier;
        private final int id;

        Player(CyclicBarrier barrier, int id) {
            this.barrier = barrier;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("玩家 " + id + " 正在进入房间...");
                Thread.sleep((long) (Math.random() * 2000));
                System.out.println("玩家 " + id + " 已到达准备区！");
                barrier.await(); // 等待所有玩家到齐
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
