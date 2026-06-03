package HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**

 * ConcurrentHashMapDemo demonstrates the usage of ConcurrentHashMap
 * in a multi-threaded environment to achieve thread-safe operations.
 *
 * In this example, multiple threads concurrently perform put operations
 * on a shared ConcurrentHashMap instance. A CountDownLatch is used to
 * synchronize the completion of all threads before verifying the final result.
 *
 * Unlike HashMap, ConcurrentHashMap ensures data consistency and correctness
 * under concurrent access by using a combination of CAS (Compare-And-Swap)
 * and fine-grained locking mechanisms. This allows multiple threads to
 * operate on different segments of the map simultaneously without blocking
 * the entire structure.
 *
 * This demo highlights how ConcurrentHashMap avoids common concurrency
 * issues such as data loss, inconsistent state, and race conditions,
 * making it a preferred choice for high-performance concurrent applications.
 */

public class ConcurrentHashMapDemo {


    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        int threadCount = 10;
        int perThreadOps = 1000;

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int base = i * perThreadOps;

            new Thread(() -> {
                for (int j = 0; j < perThreadOps; j++) {
                    map.put(base + j, j);
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        System.out.println("Expected size: " + (threadCount * perThreadOps));
        System.out.println("Actual size: " + map.size());
    }

}

