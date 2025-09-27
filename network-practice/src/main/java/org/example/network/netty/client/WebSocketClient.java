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
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

/**
 * 基于 Netty 的 WebSocket 客户端示例
 * 功能：
 * 1. 连接 WebSocket 服务器
 * 2. 发送文本消息
 * 3. 接收并打印服务器返回消息
 *
 * @author yjz
 */
public class WebSocketClient {

    private final String uri;

    public WebSocketClient(String uri) {
        this.uri = uri;
    }

    public void start() throws Exception {
        URI websocketURI = new URI(uri);
        String host = websocketURI.getHost();
        int port = websocketURI.getPort();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            // HttpClientCodec: HTTP 请求解码器 + 响应编码器
                            pipeline.addLast(new HttpClientCodec());

                            // 聚合 HTTP 消息为 FullHttpRequest
                            pipeline.addLast(new HttpObjectAggregator(65536));

                            // 自定义 Handler
                            pipeline.addLast(new WebSocketClientHandler(websocketURI));
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebSocketClient("ws://localhost:8081/ws").start();
    }

    /**
     * WebSocket 客户端消息处理器
     */
    public static class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

        private final URI uri;
        private WebSocketClientHandshaker handshaker;

        public WebSocketClientHandler(URI uri) {
            this.uri = uri;
            this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13, null, false, null);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            // 发起 WebSocket 握手
            handshaker.handshake(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (!handshaker.isHandshakeComplete()) {
                handshaker.finishHandshake(ctx.channel(), (FullHttpResponse) msg);
                System.out.println("WebSocket 握手成功!");
                // 发送测试消息
                ctx.channel().writeAndFlush(new TextWebSocketFrame("Hello Netty WebSocket Server!"));
                return;
            }

            if (msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) msg;
                System.out.println("收到服务器消息: " + textFrame.text());
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
