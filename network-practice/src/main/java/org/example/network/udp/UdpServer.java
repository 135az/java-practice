package org.example.network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author yjz
 */
public class UdpServer {
    public static void main(String[] args) {
        // 服务端监听端口
        int port = 9999;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP 服务端启动，监听端口 " + port);

            byte[] buffer = new byte[1024];
            while (true) {
                // 1. 接收客户端数据
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                // 阻塞等待数据
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("收到客户端消息: " + msg);

                // 2. 回复确认消息
                String response = "服务端已收到: " + msg;
                byte[] responseBytes = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes,
                        responseBytes.length,
                        packet.getAddress(),
                        packet.getPort()
                );
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
