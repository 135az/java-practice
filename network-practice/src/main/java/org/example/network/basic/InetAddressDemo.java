package org.example.network.basic;

import java.net.InetAddress;

/**
 * @author yjz
 */
public class InetAddressDemo {
    public static void main(String[] args) throws Exception {
        // 获取本机 IP
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("本机 IP 地址: " + localHost.getHostAddress());
        System.out.println("本机主机名: " + localHost.getHostName());

        // 获取远程主机信息
        InetAddress remote = InetAddress.getByName("www.google.com");
        System.out.println("Google IP 地址: " + remote.getHostAddress());
        System.out.println("Google 主机名: " + remote.getHostName());
    }
}
