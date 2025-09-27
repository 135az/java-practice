package org.example.network.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty Echo 客户端
 * <p>
 * 功能：
 * 1. 连接到 EchoServer
 * 2. 发送消息
 * 3. 接收服务器返回的消息
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new EchoClientHandler());
                        }
                    });

            // 连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("EchoClient 已连接到服务器 " + host + ":" + port);

            // 阻塞等待客户端关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("localhost", 8080).start();
    }

    /**
     * 自定义客户端 Handler
     */
    static class EchoClientHandler extends SimpleChannelInboundHandler<Object> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            // 连接建立后发送消息
            String message = "Hello Netty!";
            System.out.println("发送消息给服务器: " + message);
            ctx.writeAndFlush(message);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            // 接收服务器返回的消息
            System.out.println("接收到服务器返回: " + msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
