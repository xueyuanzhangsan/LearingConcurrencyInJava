package Executor;

import java.util.concurrent.*;

/**

 * ScheduledThreadPoolExecutorDemo demonstrates how to use a
 * ScheduledExecutorService to execute tasks with delay and at fixed intervals.
 *
 * In this example, two types of tasks are scheduled:
 * * A one-time delayed task using schedule(), which executes once after a specified delay
 * * A periodic task using scheduleAtFixedRate(), which runs repeatedly at a fixed interval
 *
 * The demo illustrates how ScheduledThreadPoolExecutor manages time-based
 * task execution using a pool of worker threads, allowing multiple scheduled
 * tasks to run concurrently.
 *
 * It also highlights an important aspect of scheduled execution: the main
 * thread must remain alive long enough for scheduled tasks to execute,
 * otherwise the JVM may terminate before tasks are triggered.
 *
 * This approach is commonly used in real-world scenarios such as task scheduling,
 * heartbeat checks, monitoring systems, and periodic data processing.
 */

public class ScheduledThreadPoolExecutorDemo {

    public static void main(String[] args) throws InterruptedException {

        // Create a scheduled thread pool with 2 worker threads
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        // Schedule a one-time task:
        // This task will be executed once after a delay of 2 seconds
        scheduler.schedule(() ->
                        System.out.println("Delayed task executed"),
                2, TimeUnit.SECONDS);

        // Schedule a periodic task (fixed rate):
        // initialDelay = 1 second (first execution after 1s)
        // period = 2 seconds (run every 2s regardless of task duration)
        scheduler.scheduleAtFixedRate(() ->
                        System.out.println("Periodic task running: " + System.currentTimeMillis()),
                1, 2, TimeUnit.SECONDS
        );

        // Keep the main thread alive for 5 seconds
        // Otherwise the program may exit before scheduled tasks execute
        Thread.sleep(5000);

        // Gracefully shutdown the scheduler:
        // no new tasks will be accepted, existing tasks will continue
        scheduler.shutdown();
    }
}

