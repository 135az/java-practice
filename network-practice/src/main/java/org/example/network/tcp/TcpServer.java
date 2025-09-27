package org.example.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yjz
 */
public class TcpServer {
    public static void main(String[] args) {
        // 服务端端口
        int port = 8888;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP 服务端启动，监听端口 " + port);

            while (true) {
                // 1. 接受客户端连接
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接: " + clientSocket.getInetAddress());

                // 2. 获取输入输出流
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()), true);

                // 3. 读取客户端消息并回复
                String msg;
                while ((msg = reader.readLine()) != null) {
                    System.out.println("收到客户端消息: " + msg);
                    writer.println("服务端已收到: " + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
