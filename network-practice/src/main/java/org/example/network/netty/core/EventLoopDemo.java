package org.example.network.netty.core;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * EventLoop 演示
 * EventLoop 是 Netty 的事件循环核心
 * 可以执行普通任务和延迟任务
 * 每个 EventLoop 可以处理多个 Channel 的事件，提高效率
 *
 * @author yjz
 */
public class EventLoopDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 NIO EventLoopGroup，类似线程池
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        try {
            // 获取一个 EventLoop
            EventLoop loop = group.next();

            // 提交任务，EventLoop 异步执行
            loop.submit(() -> {
                System.out.println("任务执行线程: " + Thread.currentThread().getName());
            });

            // 延迟任务演示
            loop.schedule(() -> {
                System.out.println("延迟 1 秒执行任务: " + Thread.currentThread().getName());
            }, 1, java.util.concurrent.TimeUnit.SECONDS);

            Thread.sleep(2000); // 等待任务执行
        } finally {
            group.shutdownGracefully();
        }
    }
}
