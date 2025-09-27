package org.example.network.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 基于 Netty 的 WebSocket 服务器示例
 * 功能：
 * 1. 支持 WebSocket 协议
 * 2. 异步非阻塞
 * 3. 打印客户端消息并返回确认
 *
 * @author yjz
 */
public class WebSocketServer {

    private final int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        // bossGroup: 接收连接, workerGroup: 处理 I/O
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 打印服务器启动日志
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // pipeline 是处理器链，消息会依次经过每个 handler
                            ChannelPipeline pipeline = ch.pipeline();

                            // HttpServerCodec: HTTP 请求解码器 + 响应编码器
                            pipeline.addLast(new HttpServerCodec());

                            // HttpObjectAggregator: 将 HTTP 消息聚合成 FullHttpRequest
                            pipeline.addLast(new HttpObjectAggregator(65536));

                            // WebSocketServerProtocolHandler: 处理 WebSocket 握手、ping/pong、close
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                            // 自定义 Handler: 处理文本消息
                            pipeline.addLast(new WebSocketFrameHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            System.out.println("WebSocket Server 已启动，端口: " + port);

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new WebSocketServer(8081).start();
    }

    /**
     * WebSocket 文本消息处理器
     */
    public static class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
            String receivedText = msg.text();
            System.out.println("收到消息: " + receivedText);

            // 返回消息给客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器已收到: " + receivedText));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
