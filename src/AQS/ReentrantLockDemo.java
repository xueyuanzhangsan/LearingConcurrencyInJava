package AQS;

import java.util.concurrent.locks.ReentrantLock;
/**

 * ReentrantLockDemo demonstrates the usage of ReentrantLock for
 * thread synchronization in a multi-threaded environment.
 *
 * In this example, multiple threads attempt to acquire the same lock
 * to execute a critical section. The demo highlights the reentrancy
 * feature, where the same thread can acquire the lock multiple times
 * without causing a deadlock, as long as it releases the lock the same
 * number of times.
 *
 * The example also illustrates proper lock handling using try-finally
 * blocks to ensure that the lock is always released, preventing
 * potential deadlocks.
 *
 * ReentrantLock provides more flexibility than synchronized, including
 * features such as fairness policies, interruptible lock acquisition,
 * and non-blocking attempts (tryLock), making it suitable for
 * high-performance concurrent applications.
 */

public class ReentrantLockDemo {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {

                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock");

                    // Demonstrate reentrancy (same thread acquires lock again)
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + " re-acquired lock");
                        Thread.sleep(1000);
                    } finally {
                        lock.unlock();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released lock");
                }

            }, "Thread-" + i).start();
        }
    }

}
