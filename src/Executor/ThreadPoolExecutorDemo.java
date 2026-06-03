package Executor;

import java.util.concurrent.*;

/**

 * ThreadPoolExecutorDemo demonstrates the internal working mechanism
 * of ThreadPoolExecutor, including thread creation, task queuing,
 * and rejection handling.
 *
 * The execution flow of ThreadPoolExecutor follows these steps:
 *
 * 1. If the number of running threads is less than corePoolSize,
 * a new thread is created to handle the incoming task.
 *
 * 2. Once the core threads are fully utilized, new tasks are placed
 * into the task queue instead of creating new threads.
 *
 * 3. If the queue becomes full, additional threads are created
 * (up to maximumPoolSize) to handle the overflow tasks.
 *
 * 4. If both the queue is full and the maximum number of threads
 * has been reached, the configured rejection policy is applied.
 *
 * This design allows ThreadPoolExecutor to balance resource usage
 * and throughput by combining thread reuse and task buffering.
 *
 * This demo is intended to help visualize how tasks flow through
 * the thread pool and how thread expansion and rejection occur
 * under high load.
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                      // corePoolSize:
                // Number of core threads. These threads are created first
                // and are kept alive even when idle.

                4,                      // maximumPoolSize:
                // Maximum number of threads allowed in the pool.
                // New threads are created only when the queue is full.

                10, TimeUnit.SECONDS,   // keepAliveTime:
                // Time that non-core threads will remain idle before being terminated.

                new ArrayBlockingQueue<>(2), // workQueue:
                // Task queue with small capacity to easily trigger
                // thread expansion and rejection scenarios.

                new ThreadPoolExecutor.AbortPolicy()
                // Rejection policy:
                // When the pool is at maximum size and the queue is full,
                // this policy throws RejectedExecutionException.
        );

        for (int i = 0; i < 10; i++) {
            int taskId = i;

            try {
                executor.execute(() -> {

                    // Print current thread name to observe thread reuse and expansion
                    System.out.println(Thread.currentThread().getName() +
                            " executing task " + taskId);

                    try {
                        Thread.sleep(2000); // Simulate long-running task
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (RejectedExecutionException e) {

                // This block is triggered when the rejection policy is applied
                System.out.println("Task " + taskId + " rejected!");
            }

            // Key observation point: monitor thread pool state
            System.out.println(
                    "Pool size: " + executor.getPoolSize() +        // current number of threads
                            ", Active: " + executor.getActiveCount() +      // threads currently executing tasks
                            ", Queue size: " + executor.getQueue().size()   // number of tasks waiting in queue
            );
        }

        executor.shutdown(); // Gracefully shut down the executor
    }

}


