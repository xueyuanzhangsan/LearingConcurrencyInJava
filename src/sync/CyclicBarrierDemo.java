package sync;

import java.util.concurrent.CyclicBarrier;

/**

 * CyclicBarrierDemo demonstrates how to use CyclicBarrier to coordinate
 * multiple threads in a multi-phase execution scenario.
 *
 * In this example, several threads perform work in parallel during the first phase.
 * Each thread waits at a synchronization point (barrier) after completing its task.
 * Once all threads reach the barrier, a predefined barrier action is executed,
 * and all threads proceed to the next phase simultaneously.
 *
 * This pattern is commonly used in scenarios such as parallel computations,
 * batch processing, or distributed task coordination, where multiple threads
 * must reach a certain point before continuing execution.
 */

public class CyclicBarrierDemo {

    public static void main(String[] args) {

        int threadCount = 3;

        // Create a CyclicBarrier with a fixed number of threads
        // The barrier action will be executed once all threads reach the barrier
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            System.out.println("=== All threads finished phase 1, starting phase 2 ===");
        });

        for (int i = 0; i < threadCount; i++) {
            int id = i;

            new Thread(() -> {
                try {
                    // Phase 1: simulate some work (e.g., data processing)
                    System.out.println("Thread " + id + " starts phase 1");
                    Thread.sleep(1000 + id * 1000);

                    System.out.println("Thread " + id + " completed phase 1, waiting for others");

                    // Wait until all threads reach this point
                    barrier.await();

                    // Phase 2: continue processing after synchronization
                    System.out.println("Thread " + id + " starts phase 2");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}