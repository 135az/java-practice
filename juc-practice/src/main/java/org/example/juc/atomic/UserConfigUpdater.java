package org.example.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用 AtomicReference 管理用户配置
 *
 * @author yjz
 */
public class UserConfigUpdater {
    static class Config {
        String version;
        String feature;

        Config(String version, String feature) {
            this.version = version;
            this.feature = feature;
        }

        @Override
        public String toString() {
            return "Config{version='" + version + "', feature='" + feature + "'}";
        }
    }

    private static final AtomicReference<Config> CONFIG_REF =
            new AtomicReference<>(new Config("v1", "基础功能"));

    /**
     * 更新配置
     */
    public static void updateConfig(Config newConfig) {
        CONFIG_REF.set(newConfig); // 原子替换
    }

    /**
     * 获取当前配置
     */
    public static Config getConfig() {
        return CONFIG_REF.get();
    }

    public static void main(String[] args) {
        System.out.println("初始配置：" + getConfig());

        // 模拟线程更新配置
        Thread t1 = new Thread(() -> updateConfig(new Config("v2", "新增推荐功能")));
        Thread t2 = new Thread(() -> updateConfig(new Config("v3", "新增搜索功能")));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("最终配置：" + getConfig());
    }
}
