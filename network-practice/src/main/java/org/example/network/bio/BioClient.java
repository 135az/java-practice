package org.example.network.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;

        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("BIO 客户端已连接，输入消息回车发送（输入 exit 退出）：");

            while (true) {
                String msg = scanner.nextLine();
                if ("exit".equalsIgnoreCase(msg)) {
                    break;
                }

                // 1. 发送消息
                writer.println(msg);

                // 2. 阻塞等待服务端回复
                String response = reader.readLine();
                System.out.println("收到服务端回复: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
