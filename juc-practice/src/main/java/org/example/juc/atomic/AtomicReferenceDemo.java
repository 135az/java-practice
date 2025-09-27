package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 演示 AtomicReference
 * 可用于原子地更新对象引用，比如更新用户信息、配置对象
 *
 * @author yjz
 */
public class AtomicReferenceDemo {
    static class User {
        String name;
        int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        User user1 = new User("张三", 20);
        User user2 = new User("李四", 25);

        AtomicReference<User> atomicRef = new AtomicReference<>(user1);

        System.out.println("初始值：" + atomicRef.get());

        // CAS 更新
        boolean success = atomicRef.compareAndSet(user1, user2);
        System.out.println("CAS 结果：" + success + "，当前值：" + atomicRef.get());
    }
}
