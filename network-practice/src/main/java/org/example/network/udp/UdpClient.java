package org.example.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author yjz
 */
public class UdpClient {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1"; // 服务端 IP
        int serverPort = 9999;          // 服务端端口

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName(serverHost);
            System.out.println("UDP 客户端启动，输入消息回车发送（输入 exit 退出）：");

            while (true) {
                String msg = scanner.nextLine();
                if ("exit".equalsIgnoreCase(msg)) {
                    break;
                }

                // 1. 发送消息到服务端
                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(
                        data, data.length, serverAddress, serverPort);
                socket.send(packet);

                // 2. 接收服务端回复
                byte[] buffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(responsePacket);

                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("收到服务端回复: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
