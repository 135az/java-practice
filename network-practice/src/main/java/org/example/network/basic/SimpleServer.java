package org.example.network.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个最基础的服务端示例：
 * - 监听端口 8888
 * - 接收客户端消息
 * - 回复确认消息
 *
 * @author yjz
 */
public class SimpleServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器已启动，等待客户端连接...");
        // 阻塞等待连接
        Socket clientSocket = serverSocket.accept();
        System.out.println("客户端已连接: " + clientSocket.getInetAddress());
        // 输入流：读取客户端消息
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // 输出流：回复客户端
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

        String msg;
        while ((msg = reader.readLine()) != null) {
            System.out.println("收到客户端消息: " + msg);
            writer.println("服务器已收到: " + msg);
            if ("exit".equalsIgnoreCase(msg)) {
                break;
            }
        }

        clientSocket.close();
        serverSocket.close();
    }
}
