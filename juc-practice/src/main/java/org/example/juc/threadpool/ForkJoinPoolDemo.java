package org.example.juc.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 演示 ForkJoinPool 的使用
 * <p>
 * 使用场景：大任务拆分成小任务并行执行，比如数组求和。
 *
 * @author yjz
 */
public class ForkJoinPoolDemo {
    // 定义一个递归任务：计算数组区间 [start, end] 的和
    static class SumTask extends RecursiveTask<Long> {
        private final int[] arr;
        private final int start, end;
        private static final int THRESHOLD = 5; // 阈值：当区间长度 <= 5 时，直接计算

        public SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start + 1;
            if (length <= THRESHOLD) {
                // 任务足够小，直接计算
                long sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += arr[i];
                }
                System.out.println(Thread.currentThread().getName() + " 计算区间 [" + start + "," + end + "] = " + sum);
                return sum;
            } else {
                // 大任务 -> 拆分成两个小任务
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(arr, start, mid);
                SumTask rightTask = new SumTask(arr, mid + 1, end);

                // fork 子任务（异步执行）
                leftTask.fork();
                rightTask.fork();

                // join 等待子任务执行完，并合并结果
                return leftTask.join() + rightTask.join();
            }
        }
    }

    public static void main(String[] args) {
        // 创建数组
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        // 创建 ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // 提交任务
        SumTask task = new SumTask(arr, 0, arr.length - 1);
        long result = pool.invoke(task);

        System.out.println("数组总和 = " + result);

        pool.shutdown();
    }
}
