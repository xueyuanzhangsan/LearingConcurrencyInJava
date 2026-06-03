package AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/**

 * AQSDemo demonstrates how to build a custom lock using
 * AbstractQueuedSynchronizer (AQS), which is the foundation
 * of many concurrency utilities in Java.
 *
 * In this example, a simple exclusive lock is implemented by
 * extending AQS and overriding the core methods such as
 * tryAcquire and tryRelease. The lock uses a state variable
 * to represent whether it is held or free, and relies on
 * CAS (Compare-And-Swap) for atomic state transitions.
 *
 * When multiple threads attempt to acquire the lock, those
 * that fail are placed into an internal FIFO queue managed
 * by AQS, where they are blocked and later resumed when the
 * lock becomes available.
 *
 * This demo helps illustrate the internal mechanism of
 * lock implementations such as ReentrantLock, and provides
 * insight into how thread synchronization is achieved using
 * low-level concurrency primitives.
 */

public class AQSDemo {

    public static void main(String[] args) {

        SimpleLock lock = new SimpleLock();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " released lock");
                }
            }, "Thread-" + i).start();
        }
    }

    /**
     * Custom lock implemented using AQS
     */
    static class SimpleLock {

        private final Sync sync = new Sync();

        public void lock() {
            sync.acquire(1);
        }

        public void unlock() {
            sync.release(1);
        }

        /**
         * Core AQS implementation
         */
        static class Sync extends AbstractQueuedSynchronizer {

            // Try to acquire lock
            @Override
            protected boolean tryAcquire(int arg) {
                // state = 0 means unlocked
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            // Try to release lock
            @Override
            protected boolean tryRelease(int arg) {
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }

            // Whether lock is held
            @Override
            protected boolean isHeldExclusively() {
                return getState() == 1;
            }
        }
    }

}

