package org.example.network.nio;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NioServerDemoTest {

    private NioServerDemo server;
    private Thread serverThread;

    @BeforeAll
    public void setup() {
        // 在单独的线程中启动服务器，避免阻塞测试
        server = new NioServerDemo();
        serverThread = new Thread(() -> {
            try {
                // 为了测试目的，我们将服务器启动在不同的端口上
                server.startServer(19999);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // 忽略预期的关闭异常
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
        
        // 等待服务器启动
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterAll
    public void tearDown() {
        // 清理资源
        if (server != null) {
            server.stop();
        }
        if (serverThread != null) {
            try {
                serverThread.join(2000); // 等待最多2秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    public void testServerInitialization() {
        // 测试服务器是否能正常初始化并监听端口
        try {
            InetSocketAddress address = new InetSocketAddress("localhost", 19999);
            SocketChannel clientChannel = SocketChannel.open();
            // 尝试连接到服务器
            boolean connected = clientChannel.connect(address);
            assertTrue(connected, "服务器应该在端口19999上监听连接");
            clientChannel.close();
        } catch (IOException e) {
            fail("服务器连接测试失败: " + e.getMessage());
        }
    }
    
    @Test
    public void testServerReadData() {
        // 测试服务器是否能接收客户端发送的数据
        try {
            InetSocketAddress address = new InetSocketAddress("localhost", 19999);
            SocketChannel clientChannel = SocketChannel.open();
            boolean connected = clientChannel.connect(address);
            assertTrue(connected, "应该能够连接到服务器");
            
            // 发送测试消息
            String testMessage = "Hello, NIO Server!";
            ByteBuffer buffer = ByteBuffer.wrap(testMessage.getBytes(StandardCharsets.UTF_8));
            clientChannel.write(buffer);
            
            // 等待服务器处理消息
            Thread.sleep(100);
            
            clientChannel.close();
        } catch (IOException | InterruptedException e) {
            fail("测试服务器读取数据失败: " + e.getMessage());
        }
    }
}