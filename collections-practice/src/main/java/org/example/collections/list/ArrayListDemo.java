package org.example.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * ArrayListDemo
 * <p>
 * 用来总结 ArrayList 的常见用法和底层原理。
 * - 特点：基于动态数组实现，支持随机访问，增删中间元素代价较大。
 * - 默认容量：10
 * - 扩容机制：扩容为原来容量的 1.5 倍
 *
 * @author yjz
 */
public class ArrayListDemo {

    public static void main(String[] args) {
        // 1. 创建 ArrayList
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("C++");

        System.out.println("初始列表: " + list);

        // 2. 指定位置插入
        list.add(1, "Go");
        System.out.println("插入后: " + list);

        // 3. 根据索引访问
        System.out.println("第2个元素: " + list.get(1));

        // 4. 修改元素
        list.set(2, "Rust");
        System.out.println("修改后: " + list);

        // 5. 删除元素
        list.remove("Java");
        list.remove(1); // 按索引删除
        System.out.println("删除后: " + list);

        // 6. 判断是否包含某个元素
        System.out.println("是否包含 Python? " + list.contains("Python"));

        // 7. 转数组
        String[] arr = list.toArray(new String[0]);
        System.out.println("数组形式: " + Arrays.toString(arr));

        // 8. 遍历方式
        System.out.print("for-each 遍历: ");
        for (String lang : list) {
            System.out.print(lang + " ");
        }
        System.out.println();

        System.out.print("Iterator 遍历: ");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        // 9. subList 示例
        list.add("Kotlin");
        list.add("Scala");
        List<String> subList = list.subList(0, 2);
        System.out.println("子列表 (0~2): " + subList);

        // 10. 注意：subList 是原始列表的视图
        subList.set(0, "TypeScript");
        System.out.println("修改子列表后，原列表: " + list);

        // 11. 扩容机制演示
        testArrayListExpansion();

        // 12. 常见坑：线程不安全
        // 多线程环境下推荐使用 CopyOnWriteArrayList
    }

    /**
     * 扩容机制演示
     */
    private static void testArrayListExpansion() {
        ArrayList<Integer> numbers = new ArrayList<>();
        int oldCapacity = 0;

        try {
            java.lang.reflect.Field elementDataField =
                    ArrayList.class.getDeclaredField("elementData");
            elementDataField.setAccessible(true);

            for (int i = 0; i < 20; i++) {
                numbers.add(i);

                Object[] elementData = (Object[]) elementDataField.get(numbers);
                int capacity = elementData.length;

                if (capacity != oldCapacity) {
                    System.out.println("第 " + (i + 1) + " 次 add() -> 触发扩容，容量变为 " + capacity);
                    oldCapacity = capacity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * ArrayList 扩容机制总结：
 * <p>
 * 🔹 默认容量
 * - 第一次 add() 时，elementData 容量会初始化为 DEFAULT_CAPACITY (10)。
 * <p>
 * 🔹 扩容源码（JDK 1.8）
 * private void grow(int minCapacity) {
 * int oldCapacity = elementData.length;
 * int newCapacity = oldCapacity + (oldCapacity >> 1); // 右移一位 = 除以2，即扩容为 1.5 倍
 * if (newCapacity - minCapacity < 0)
 * newCapacity = minCapacity; // 保证至少够用
 * if (newCapacity - MAX_ARRAY_SIZE > 0)
 * newCapacity = hugeCapacity(minCapacity);
 * elementData = Arrays.copyOf(elementData, newCapacity);
 * }
 * <p>
 * 🔹 调试结果（testArrayListExpansion 输出）：
 * 第  1 次 add() -> 触发扩容，容量变为 10
 * ↳ 对应源码：第一次 add()，elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA，
 * grow() 将容量置为 DEFAULT_CAPACITY (10)
 * <p>
 * 第 11 次 add() -> 触发扩容，容量变为 15
 * ↳ 对应源码：oldCapacity = 10，newCapacity = 10 + 10/2 = 15
 * <p>
 * 第 16 次 add() -> 触发扩容，容量变为 22
 * ↳ 对应源码：oldCapacity = 15，newCapacity = 15 + 15/2 = 22
 * <p>
 * 🔹 面试话术示例：
 * - ArrayList 初始容量是 10。
 * - 扩容时按照 1.5 倍扩容，具体公式是 old + old>>1。
 * - 如果 1.5 倍不够，则直接取 minCapacity。
 * - 如果超过 MAX_ARRAY_SIZE，会走 hugeCapacity 逻辑。
 */

