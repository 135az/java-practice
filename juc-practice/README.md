# JUC Practice

本模块系统总结了 Java 并发编程（JUC）中 **核心工具类、并发容器、线程池** 以及 **实际开发场景**，帮助你从入门到进阶。

## 学习路线

1. **基础篇 basics**
    - 线程的基本使用：Thread / Runnable / Callable
    - ExecutorService 入门
    - ScheduledExecutor 定时任务

2. **锁机制 locks**
    - synchronized 关键字
    - ReentrantLock 可重入锁
    - ReadWriteLock 读写锁
    - StampedLock 乐观读锁
    - 死锁案例与排查

3. **原子类 atomics**
    - AtomicInteger / AtomicReference
    - LongAdder 与高并发计数器
    - CAS 与 ABA 问题

4. **并发工具类 synchronizers**
    - CountDownLatch：线程等待
    - CyclicBarrier：栅栏，分段同步
    - Semaphore：限流、资源控制
    - Phaser：分阶段任务控制
    - Exchanger：线程间数据交换

5. **并发容器 concurrent_collections**
    - ConcurrentHashMap
    - CopyOnWriteArrayList
    - BlockingQueue 系列
    - PriorityBlockingQueue

6. **线程池 threadpool**
    - ThreadPoolExecutor 参数详解
    - 自定义线程工厂与拒绝策略
    - ForkJoinPool 分治并行计算

7. **异步编程 async**
    - CompletableFuture 高级异步控制
    - ParallelStream 并行流
    - 异步任务流水线

8. **实战案例 realworld**
    - 多线程批量插入数据库
    - 文件分块处理
    - 信号量限流
    - 并发缓存
    - 生产者-消费者模型

9. **进阶 advanced**
    - False Sharing 与缓存行对齐
    - ThreadLocal 使用场景与风险
    - Disruptor 入门
    - 分段锁

---

## 📚 模块架构设计

```aiignore
juc-practice
 ├── README.md                # 模块说明与学习路径
 └── src/main/java/com/example/juc
     ├── basics                # JUC 基础（线程、Runnable/Callable、线程池入门）
     │    ├── ThreadDemo.java
     │    ├── RunnableDemo.java
     │    ├── CallableFutureDemo.java
     │    ├── ExecutorServiceDemo.java
     │    └── ScheduledExecutorDemo.java
     │
     ├── locks                 # 锁机制（synchronized、ReentrantLock、读写锁等）
     │    ├── SynchronizedDemo.java
     │    ├── ReentrantLockDemo.java
     │    ├── ReadWriteLockDemo.java
     │    ├── StampedLockDemo.java
     │    └── DeadlockDemo.java
     │
     ├── atomics               # 原子类（CAS 实现）
     │    ├── AtomicIntegerDemo.java
     │    ├── AtomicReferenceDemo.java
     │    ├── LongAdderDemo.java
     │    └── ABAProblemDemo.java
     │
     ├── synchronizers          # 并发工具类（开发中高频使用）
     │    ├── CountDownLatchDemo.java
     │    ├── CyclicBarrierDemo.java
     │    ├── SemaphoreDemo.java
     │    ├── PhaserDemo.java
     │    └── ExchangerDemo.java
     │
     ├── concurrent_collections # 并发容器（高性能替代品）
     │    ├── ConcurrentHashMapDemo.java
     │    ├── CopyOnWriteArrayListDemo.java
     │    ├── BlockingQueueDemo.java
     │    └── PriorityBlockingQueueDemo.java
     │
     ├── threadpool             # 线程池高级应用
     │    ├── ThreadPoolExecutorDemo.java
     │    ├── CustomThreadFactoryDemo.java
     │    ├── RejectedExecutionHandlerDemo.java
     │    └── ForkJoinPoolDemo.java
     │
     ├── async                  # 异步编程
     │    ├── CompletableFutureDemo.java
     │    ├── ParallelStreamDemo.java
     │    └── AsyncPipelineDemo.java
     │
     ├── realworld              # 实际开发中的综合案例
     │    ├── BatchInsertWithCountDownLatch.java    # 多线程批量插入数据库
     │    ├── FileProcessingWithThreadPool.java     # 线程池处理大文件
     │    ├── RateLimiterWithSemaphore.java         # 限流器实现
     │    ├── CacheWithReadWriteLock.java           # 并发缓存实现
     │    └── ProducerConsumerWithBlockingQueue.java# 生产者消费者模式
     │
     └── advanced               # 深入进阶
          ├── FalseSharingDemo.java                 # 伪共享问题
          ├── ThreadLocalDemo.java                  # ThreadLocal 使用与内存泄漏
          ├── DisruptorIntroDemo.java               # Disruptor 框架入门
          └── LockStripingDemo.java                 # 分段锁案例

```

## 🎯 目标

- 能够熟练掌握 Java 并发编程常用 API
- 能将 JUC 知识应用到实际开发场景
- 对线程安全、性能优化有深入理解
