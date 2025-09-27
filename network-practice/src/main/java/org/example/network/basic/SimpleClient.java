package org.example.network.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author yjz
 */
public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8888);
        System.out.println("已连接服务器！");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        // 模拟发送消息
        writer.println("你好，服务器！");
        System.out.println("服务器回复: " + reader.readLine());

        writer.println("exit");
        System.out.println("服务器回复: " + reader.readLine());

        socket.close();
    }
}
