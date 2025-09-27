package org.example.network.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty Echo 服务器
 * <p>
 * 功能：
 * 1. 接收客户端连接
 * 2. 将客户端发送的数据原样返回
 * 3. 演示 ChannelPipeline 和 ChannelHandler 使用
 *
 * @author yjz
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        // bossGroup 负责接收连接，workerGroup 负责处理 I/O
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 使用 NIO ServerSocketChannel
                    .handler(new LoggingHandler(LogLevel.INFO)) // 打印服务端日志
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 添加自定义 Handler
                            pipeline.addLast(new EchoServerHandler());
                        }
                    });

            // 绑定端口并启动服务器
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("EchoServer 启动在端口: " + port);

            // 阻塞等待服务器关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8080).start();
    }

    /**
     * 自定义 Handler，继承 ChannelInboundHandlerAdapter
     * 处理客户端发送的消息
     */
    static class EchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // 收到消息后原样返回给客户端
            System.out.println("接收到客户端消息: " + msg);
            ctx.write(msg); // 异步写入
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush(); // 刷新缓冲区，真正发送数据
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close(); // 出现异常关闭连接
        }
    }
}
