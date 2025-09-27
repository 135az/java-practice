package org.example.network.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务器示例类
 * 
 * 这个类演示了如何使用Java NIO (Non-blocking I/O) 创建一个简单的服务器。
 * 它使用Selector机制来处理多个客户端连接，可以同时处理多个客户端而不会阻塞。
 */
public class NioServerDemo {
    
    private volatile boolean running = true;
    
    /**
     * NIO服务器主函数，启动一个非阻塞的服务器并监听客户端连接
     * 该服务器使用Java NIO的Selector机制来处理多个客户端连接
     * 
     * @param args 命令行参数
     * @throws IOException 当IO操作出现异常时抛出
     */
    public static void main(String[] args) throws IOException {
        NioServerDemo server = new NioServerDemo();
        server.startServer(9999);
    }

    /**
     * 启动NIO服务器
     * 
     * @param port 服务器监听的端口号
     * @throws IOException 当IO操作出现异常时抛出
     */
    public void startServer(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO 服务端启动，端口 " + port + "...");

        // 主循环，持续监听和处理事件
        while (running) {
            selector.select(1000); // 设置超时避免阻塞
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                // 处理新的客户端连接请求
                if (key.isAcceptable()) {
                    handleAccept(key, selector);
                } 
                // 处理客户端发送的数据
                else if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
        
        // 关闭资源
        selector.close();
        serverChannel.close();
    }
    
    /**
     * 处理客户端连接请求
     * 
     * @param key 选择键
     * @param selector 选择器
     * @throws IOException 当IO操作出现异常时抛出
     */
    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("客户端连接：" + client.getRemoteAddress());
    }
    
    /**
     * 处理客户端发送的数据
     * 
     * @param key 选择键
     * @throws IOException 当IO操作出现异常时抛出
     */
    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readBytes = client.read(buffer);
        if (readBytes > 0) {
            buffer.flip();
            String msg = new String(buffer.array(), 0, buffer.limit());
            System.out.println("收到消息：" + msg);
            buffer.clear();
        } else if (readBytes == -1) {
            System.out.println("客户端断开：" + client.getRemoteAddress());
            client.close();
        }
    }
    
    /**
     * 停止服务器运行
     */
    public void stop() {
        running = false;
    }
    
    /**
     * 检查服务器是否正在运行
     * 
     * @return 如果服务器正在运行返回true，否则返回false
     */
    public boolean isRunning() {
        return running;
    }
}