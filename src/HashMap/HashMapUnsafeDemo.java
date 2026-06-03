package HashMap;

import java.util.HashMap;
import java.util.Map;
/**

 * HashMapUnsafeDemo demonstrates the lack of thread safety in HashMap
 * when accessed concurrently by multiple threads.
 *
 * In this example, multiple threads perform put operations on a shared
 * HashMap instance without any synchronization. Due to the absence of
 * thread-safety guarantees, race conditions may occur, leading to
 * inconsistent results such as data loss, incorrect size, or overwritten entries.
 *
 * This issue exists in all versions of HashMap, including JDK 1.8 and later,
 * as HashMap is not designed for concurrent use. While JDK 1.8 improved
 * the internal structure and reduced the risk of infinite loops during resizing,
 * it does not provide thread safety.
 *
 * This demo highlights why HashMap should not be used in multi-threaded
 * environments without proper synchronization, and why alternatives such as
 * ConcurrentHashMap should be preferred for concurrent scenarios.
 */

public class HashMapUnsafeDemo {

    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                map.put(i, i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                map.put(i, i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected size: 20000");
        System.out.println("Actual size: " + map.size());
    }
}