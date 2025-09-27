# Java 网络编程模块

本模块总结了 Java 网络编程的常见操作，涵盖 **Socket、TCP、UDP、NIO、HTTP、Netty** 等场景，并附带一个 **聊天室案例** 进行综合实践。

---

## 📂 模块结构

- `basics/`  
  Java 网络编程基础：`Socket`、`ServerSocket`、`InetAddress`、`URLConnection`。

- `tcp/`  
  TCP 通信示例：`TcpClient` 和 `TcpServer`。

- `udp/`  
  UDP 通信示例：`UdpClient` 和 `UdpServer`。

- `nio/`  
  Java NIO 示例：`NioServer`、`NioClient`。

- `http/`  
  Java HTTP 通信：
    - `HttpUrlConnectionDemo`
    - `HttpClientDemo`
    - `SimpleHttpServer`

- `chatroom/`  
  综合案例：基于 `Socket` 的多人聊天室。

- `netty/`  
  Netty 框架使用示例：
    - `echo/` → Echo 服务端与客户端
    - `http/` → 基于 Netty 的 HTTP 服务
    - `websocket/` → WebSocket 服务与客户端

---

## 🚀 学习路径

1. **基础入门** → `basics` 包，理解 Socket、IP、端口。
2. **TCP 与 UDP 对比** → 掌握面向连接与无连接通信。
3. **NIO 非阻塞 IO** → 理解 Selector、Channel、Buffer。
4. **HTTP 编程** → 调用 REST API 与简易 Web 服务。
5. **Netty 框架** → 掌握高性能异步网络编程。
6. **综合实践** → 多人聊天室、Netty WebSocket。

---

## 🛠️ 环境准备

- JDK 8+（推荐 JDK 11+）
- Maven 3.x
- Netty 依赖：
  ```xml
  <dependency>
      <groupId>io.netty</groupId>
      <artifactId>netty-all</artifactId>
      <version>4.1.100.Final</version>
  </dependency>
