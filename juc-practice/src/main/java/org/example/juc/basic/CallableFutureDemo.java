package org.example.juc.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用 Callable + FutureTask 获取线程返回值
 *
 * @author yjz
 */
public class CallableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 Callable 任务（有返回值）
        Callable<Integer> task = () -> {
            System.out.println("计算任务执行中...");
            Thread.sleep(1000);
            return 123;
        };

        // 包装成 FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        // 创建线程并执行
        Thread t = new Thread(futureTask);
        t.start();

        // 获取返回值（会阻塞直到任务完成）
        Integer result = futureTask.get();
        System.out.println("任务结果：" + result);
    }
}
