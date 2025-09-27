package org.example.network.netty.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty Channel 演示
 * Channel 是 Netty 的核心抽象，表示网络连接。
 *
 * @author yjz
 */
public class ChannelDemo {

    public static void main(String[] args) throws InterruptedException {
        // bossGroup 用于接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // workerGroup 用于处理 I/O
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            // Pipeline 中可以添加多个 Handler
                            ch.pipeline().addLast(new SimpleChannelHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(8080).sync();
            System.out.println("Netty 服务器启动在端口 8080");

            // 获取 ServerChannel，理解 Channel 代表连接的抽象
            Channel serverChannel = future.channel();
            System.out.println("ServerChannel 是否活跃: " + serverChannel.isActive());

            // 阻塞等待服务器关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class SimpleChannelHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("收到客户端消息: " + msg);
            ctx.writeAndFlush(msg); // 回写给客户端
        }
    }
}
