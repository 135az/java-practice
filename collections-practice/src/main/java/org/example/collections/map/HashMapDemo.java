package org.example.collections.map;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMapDemo
 * <p>
 * - HashMap 基于数组 + 链表（JDK1.7）/ 数组 + 链表 + 红黑树（JDK1.8）
 * - 默认容量：16
 * - 装载因子：0.75
 * - 扩容条件：元素个数 > 容量 * 装载因子
 * - 扩容倍数：2 倍
 *
 * @author yjz
 */
public class HashMapDemo {

    public static void main(String[] args) {
        // 1. 创建 HashMap
        Map<String, Integer> map = new HashMap<>();
        map.put("Java", 1);
        map.put("Python", 2);
        map.put("C++", 3);

        System.out.println("初始 map: " + map);

        // 2. 基本操作
        System.out.println("取值: Python -> " + map.get("Python"));
        System.out.println("是否包含 key 'Java': " + map.containsKey("Java"));
        System.out.println("是否包含 value 2: " + map.containsValue(2));

        // 3. putIfAbsent
        map.putIfAbsent("Java", 100); // 已存在，不会覆盖
        map.putIfAbsent("Go", 4);     // 不存在，新增
        System.out.println("putIfAbsent 后: " + map);

        // 4. getOrDefault
        System.out.println("获取存在的 key: " + map.getOrDefault("Go", -1));
        System.out.println("获取不存在的 key: " + map.getOrDefault("Rust", -1));

        // 5. replace
        map.replace("Python", 200);
        System.out.println("replace 后: " + map);

        // 6. replace(key, oldValue, newValue)
        boolean replaced = map.replace("C++", 3, 300);
        System.out.println("replace(key,old,new) 是否成功: " + replaced + ", map: " + map);

        // 7. computeIfAbsent
        map.computeIfAbsent("Rust", k -> 500);
        System.out.println("computeIfAbsent 后: " + map);

        // 8. computeIfPresent
        map.computeIfPresent("Java", (k, v) -> v + 1000);
        System.out.println("computeIfPresent 后: " + map);

        // 9. compute
        map.compute("Scala", (k, v) -> (v == null) ? 10 : v + 10);
        System.out.println("compute 后: " + map);

        // 10. merge
        map.merge("Go", 1, Integer::sum); // Go 原本是 4 -> 4+1
        map.merge("Kotlin", 1, Integer::sum); // 不存在则新增
        System.out.println("merge 后: " + map);

        // 11. replaceAll
        map.replaceAll((k, v) -> v * 2);
        System.out.println("replaceAll 后: " + map);

        // 12. remove(key, value)
        boolean removed = map.remove("Go", 10);
        System.out.println("remove(key,value) 是否成功: " + removed + ", map: " + map);

        // 13. 遍历
        System.out.println("遍历方式 1：entrySet");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("遍历方式 2：keySet");
        for (String key : map.keySet()) {
            System.out.println(key + " -> " + map.get(key));
        }
    }
}


