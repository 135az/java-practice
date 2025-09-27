package org.example.network.netty.basic;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;


/**
 * @author yjz
 */
public class NettyOverview {

    public static void main(String[] args) throws InterruptedException {
        /*
         * Netty 核心概念：
         * 1. Channel：代表一个连接，可以是 TCP 连接，也可以是 UDP 连接，类似 Socket。
         * 2. EventLoop：事件循环，负责处理 Channel 的所有事件，包括读、写、连接等。
         * 3. EventLoopGroup：EventLoop 的集合，一般服务端用它来管理多个 Channel 的线程。
         * 4. Pipeline：ChannelHandler 的链条，所有事件都会沿着 Pipeline 传递。
         * 5. Handler：处理事件的逻辑单元，可分为 Inbound（入站）和 Outbound（出站）。
         */

        // 创建一个 NIO 类型的 EventLoopGroup，类似于线程池，用于处理所有 Channel 事件
        EventLoopGroup group = new NioEventLoopGroup(2);

        try {
            // 获取 EventLoop 对象
            EventLoop eventLoop = group.next();

            // 模拟向 EventLoop 提交任务，事件处理都是异步的
            eventLoop.submit(() -> System.out.println("Hello from Netty EventLoop!"));

            // 输出 EventLoop 线程信息
            System.out.println("EventLoop 线程名: " + Thread.currentThread().getName());
        } finally {
            // 关闭 EventLoopGroup，释放资源
            group.shutdownGracefully();
        }
    }
}
