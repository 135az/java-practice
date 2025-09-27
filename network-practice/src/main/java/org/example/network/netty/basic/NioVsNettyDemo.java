package org.example.network.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 对比 Java NIO 与 Netty 的区别
 *
 * @author yjz
 */
public class NioVsNettyDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("==== 阻塞 IO 示例 ====");
        startBlockingServer();

        System.out.println("==== Netty 异步 NIO 示例 ====");
        startNettyServer();
    }

    // 阻塞 IO 模拟服务端
    private static void startBlockingServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8081)) {
                while (true) {
                    // 阻塞等待客户端连接
                    Socket socket = serverSocket.accept();
                    System.out.println("收到客户端连接: " + socket.getRemoteSocketAddress());
                    // 阻塞读取数据 ...
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Netty 异步 NIO 服务端
    private static void startNettyServer() throws InterruptedException {
        // bossGroup 用于接收连接，workerGroup 用于处理 I/O
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 使用 NIO ServerSocketChannel
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            // Pipeline 中可以添加多个 Handler
                            ch.pipeline().addLast(new SimpleServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(8082).sync();
            System.out.println("Netty 服务器启动在端口 8082");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 一个简单的 ChannelHandler
    static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("Netty 收到消息: " + msg);
        }
    }
}
