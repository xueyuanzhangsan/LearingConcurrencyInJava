package HashMap;

import java.util.HashMap;
/**

 * HashMapDeadLoopDemo demonstrates a classic concurrency issue in HashMap
 * that may lead to an infinite loop during resizing in multi-threaded environments.
 *
 * This problem is specific to HashMap implementations in JDK 1.7 and earlier,
 * where the internal data structure uses an array combined with linked lists
 * and inserts elements using head insertion.
 *
 * During concurrent resize operations, multiple threads may transfer nodes
 * in an inconsistent order, which can result in a cyclic linked list.
 * Once a cycle is formed, any traversal of the affected bucket may lead to
 * an infinite loop, causing CPU usage to spike to 100%.
 *
 * Starting from JDK 1.8, the HashMap implementation was improved by switching
 * to tail insertion and introducing tree-based structures (red-black trees),
 * which significantly reduces the risk of this issue. However, HashMap remains
 * not thread-safe and should not be used in concurrent scenarios without
 * proper synchronization.
 *
 * This demo is intended for educational purposes to illustrate historical
 * concurrency flaws in HashMap and should not be relied upon for deterministic
 * reproduction, as the issue depends on thread scheduling.
 */

public class HashMapDeadLoopDemo {

    public static void main(String[] args) {

        HashMap<Integer, Integer> map = new HashMap<>(2, 0.75f);

        // 多线程疯狂put，触发resize
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    map.put(j, j);
                }
            }).start();
        }
    }
}
