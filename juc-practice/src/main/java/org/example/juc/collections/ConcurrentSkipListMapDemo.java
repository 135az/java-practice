package org.example.juc.collections;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * ConcurrentSkipListMap 示例
 * 应用场景：有序集合，如排行榜。
 *
 * @author yjz
 */
public class ConcurrentSkipListMapDemo {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> ranking = new ConcurrentSkipListMap<>();

        // 添加选手分数（key 会自动排序）
        ranking.put(90, "Alice");
        ranking.put(120, "Bob");
        ranking.put(110, "Charlie");

        System.out.println("=== 排行榜 ===");
        ranking.descendingMap().forEach((score, player) ->
                System.out.println(player + " 分数: " + score));
    }
}
