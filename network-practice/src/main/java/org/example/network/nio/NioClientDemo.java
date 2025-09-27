package org.example.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO客户端示例类
 * <p>
 * 这个类演示了如何使用Java NIO (Non-blocking I/O) 创建一个简单的客户端。
 * 客户端连接到服务器并向服务器发送消息。
 *
 * @author yjz
 */
public class NioClientDemo {

    /**
     * NIO客户端主函数，连接到NIO服务器并发送消息
     * 客户端连接到本地9999端口的服务器，并发送5条消息，每条消息间隔500毫秒
     *
     * @param args 命令行参数
     * @throws IOException          当IO操作出现异常时抛出
     * @throws InterruptedException 当线程被中断时抛出
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // 连接到本地9999端口的服务器
        SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 9999));
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 发送5条消息到服务器
        for (int i = 1; i <= 5; i++) {
            String msg = "消息 " + i;
            buffer.put(msg.getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();
            Thread.sleep(500);
        }
        client.close();
    }
}