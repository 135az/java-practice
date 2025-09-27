package org.example.collections.map;

import java.util.HashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * HashMap 扩容机制演示
 */
public class HashMapDemo {

    /**
     * 扩容机制演示
     */
    private static void testHashMapExpansion() {
        HashMap<Integer, String> map = new HashMap<>();
        int oldCapacity = 0;

        try {
            // Java 9+ 推荐使用 VarHandle 来替代 setAccessible
            VarHandle tableHandle = MethodHandles.privateLookupIn(HashMap.class, MethodHandles.lookup())
                    .findVarHandle(HashMap.class, "table", Object[].class);

            for (int i = 0; i < 20; i++) {
                map.put(i, "val" + i);

                Object[] table = (Object[]) tableHandle.get(map);
                int capacity = (table == null) ? 0 : table.length;

                if (capacity != oldCapacity) {
                    System.out.println("第 " + (i + 1) + " 次 put() -> 触发扩容，容量变为 " + capacity);
                    oldCapacity = capacity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testHashMapExpansion();
    }
}