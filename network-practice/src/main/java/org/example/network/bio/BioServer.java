package org.example.network.bio;

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
public class BioServer {
    public static void main(String[] args) {
        int port = 8888;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("BIO 服务端启动，监听端口 " + port);

            while (true) {
                // 1. 阻塞等待客户端连接
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接: " + clientSocket.getInetAddress());

                // 2. 为每个客户端分配一个线程
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()), true)
        ) {
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println("收到客户端消息: " + msg);
                writer.println("服务端已收到: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
