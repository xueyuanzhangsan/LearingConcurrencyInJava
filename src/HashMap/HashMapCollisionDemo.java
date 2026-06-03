package HashMap;

import java.util.HashMap;
import java.util.Map;
/**

 * HashMapCollisionDemo demonstrates how hash collisions can amplify
 * concurrency issues in HashMap when accessed by multiple threads.
 *
 * In this example, a custom key class is used to deliberately produce
 * identical hash codes, forcing all entries into the same bucket.
 * This creates a high-collision scenario where the underlying data
 * structure degrades into a linked list (or tree in JDK 1.8+).
 *
 * When multiple threads concurrently perform put operations on this
 * shared HashMap without synchronization, race conditions become more
 * likely, leading to data inconsistency, lost updates, or structural
 * corruption.
 *
 * Although JDK 1.8 introduces improvements such as tree-based structures
 * to optimize lookup performance under heavy collisions, it does not
 * make HashMap thread-safe. Concurrent modifications can still result
 * in unpredictable behavior.
 *
 * This demo highlights the importance of proper synchronization or
 * using concurrent data structures such as ConcurrentHashMap in
 * multi-threaded environments.
 */

public class HashMapCollisionDemo {

    static class Key {
        int id;

        Key(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 1; // 强制冲突
        }
    }

    static Map<Key, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(new Key(i), i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                map.put(new Key(i), i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("size = " + map.size());
    }
}
