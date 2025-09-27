package org.example.network.netty.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Pipeline 演示
 * Pipeline 是 ChannelHandler 的链条,事件沿链条传递，可以逐个处理。
 *
 * @author yjz
 */
public class PipelineDemo {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 添加 Handler 链
                            pipeline.addLast("handler1", new SimpleHandler("Handler1"));
                            pipeline.addLast("handler2", new SimpleHandler("Handler2"));
                        }
                    });

            ChannelFuture future = bootstrap.bind(8083).sync();
            System.out.println("Netty 服务器启动在端口 8083");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    static class SimpleHandler extends ChannelInboundHandlerAdapter {
        private final String name;

        SimpleHandler(String name) {
            this.name = name;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println(name + " 收到消息: " + msg);
            ctx.fireChannelRead(msg); // 将事件传递给下一个 Handler
        }
    }
}
