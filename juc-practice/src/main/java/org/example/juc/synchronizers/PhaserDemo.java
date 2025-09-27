package org.example.juc.synchronizers;

import java.util.concurrent.Phaser;

/**
 * Phaser 使用场景：
 * 分阶段控制多个线程的执行流程。
 *
 * @author yjz
 */
public class PhaserDemo {

    public static void main(String[] args) {
        int runners = 3;
        Phaser phaser = new Phaser(runners);

        for (int i = 1; i <= runners; i++) {
            new Thread(new Runner(phaser, i)).start();
        }
    }

    static class Runner implements Runnable {
        private final Phaser phaser;
        private final int id;

        Runner(Phaser phaser, int id) {
            this.phaser = phaser;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                runStage("起跑");
                phaser.arriveAndAwaitAdvance(); // 等待所有人起跑完成

                runStage("中途");
                phaser.arriveAndAwaitAdvance();

                runStage("冲刺");
                phaser.arriveAndAwaitAdvance();

                System.out.println("🏆 选手 " + id + " 到达终点！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void runStage(String stage) throws InterruptedException {
            System.out.println("选手 " + id + " 正在 " + stage + " 阶段...");
            Thread.sleep((long) (Math.random() * 2000));
        }
    }
}
