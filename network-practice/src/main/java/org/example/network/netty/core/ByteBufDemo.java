package org.example.network.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Netty 核心数据容器 ByteBuf 演示
 * ByteBuf 是 Netty 对字节数据的封装，比 Java NIO ByteBuffer 功能更强。
 * ByteBuf 的重要特性
 * 1. 支持读写索引分开管理
 * 2. 可动态扩容
 * 3. 支持堆内存与直接内存
 *
 * @author yjz
 */
public class ByteBufDemo {

    public static void main(String[] args) {
        // 创建一个容量为 10 的 ByteBuf
        ByteBuf buf = Unpooled.buffer(10);

        // 写入数据
        for (int i = 0; i < 5; i++) {
            buf.writeByte(i);
        }

        System.out.println("写入 5 个字节后");
        System.out.println("容量: " + buf.capacity());
        System.out.println("可读字节数: " + buf.readableBytes());
        System.out.println("可写字节数: " + buf.writableBytes());

        // 读取数据
        while (buf.isReadable()) {
            System.out.print(buf.readByte() + " ");
        }
        System.out.println();
    }
}
