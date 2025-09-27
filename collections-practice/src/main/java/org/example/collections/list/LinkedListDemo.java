package org.example.collections.list;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * LinkedList 常用 API 演示
 *
 * @author yjz
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        // 1. 创建 LinkedList
        LinkedList<String> list = new LinkedList<>();

        // 2. add() 添加元素（默认尾部）
        list.add("apple");
        list.add("banana");
        list.add("orange");
        System.out.println("初始 list: " + list);

        // 3. addFirst() / addLast()
        list.addFirst("first");
        list.addLast("last");
        System.out.println("添加首尾后: " + list);

        // 4. get() 根据索引获取
        System.out.println("索引 2 的元素: " + list.get(2));

        // 5. getFirst() / getLast()
        System.out.println("第一个元素: " + list.getFirst());
        System.out.println("最后一个元素: " + list.getLast());

        // 6. set() 修改指定索引的值
        list.set(1, "blueberry");
        System.out.println("修改后的 list: " + list);

        // 7. remove() 删除指定索引或对象
        list.remove(2);
        list.remove("last");
        System.out.println("删除后的 list: " + list);

        // 8. removeFirst() / removeLast()
        list.removeFirst();
        list.removeLast();
        System.out.println("删除首尾后: " + list);

        // 9. offer() / offerFirst() / offerLast() -> 队列用法
        list.offer("pear");
        list.offerFirst("grape");
        list.offerLast("melon");
        System.out.println("使用 offer 后: " + list);

        // 10. poll() / pollFirst() / pollLast() -> 队列出队
        System.out.println("poll: " + list.poll());
        System.out.println("pollFirst: " + list.pollFirst());
        System.out.println("pollLast: " + list.pollLast());
        System.out.println("出队后的 list: " + list);

        // 11. peek() / peekFirst() / peekLast() -> 查看元素（不删除）
        list.add("kiwi");
        list.add("mango");
        System.out.println("peek: " + list.peek());
        System.out.println("peekFirst: " + list.peekFirst());
        System.out.println("peekLast: " + list.peekLast());

        // 12. size() / isEmpty()
        System.out.println("list 大小: " + list.size());
        System.out.println("是否为空: " + list.isEmpty());

        // 13. 遍历方式一：for-each
        System.out.println("--- for-each 遍历 ---");
        for (String fruit : list) {
            System.out.println(fruit);
        }

        // 14. 遍历方式二：普通 for 循环
        System.out.println("--- for 循环遍历 ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("索引 " + i + " : " + list.get(i));
        }

        // 15. 遍历方式三：迭代器
        System.out.println("--- Iterator 遍历 ---");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // 16. clear() 清空
        list.clear();
        System.out.println("清空后的 list: " + list);
    }
}
