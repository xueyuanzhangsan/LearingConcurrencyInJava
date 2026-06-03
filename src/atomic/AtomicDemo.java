package atomic;

import java.util.concurrent.atomic.*;
import java.util.concurrent.CountDownLatch;
/**

 * AtomicDemo demonstrates the usage of various atomic classes provided in
 * the java.util.concurrent.atomic package.
 *
 * This class covers different categories of atomic operations, including:
 * * Basic atomic types (AtomicInteger, AtomicLong, AtomicBoolean)
 * * Atomic arrays (AtomicIntegerArray, etc.)
 * * Atomic references (AtomicReference, AtomicStampedReference)
 * * Field updaters (AtomicIntegerFieldUpdater)
 * * High-concurrency utilities (LongAdder)
 *
 * Each example illustrates how atomic classes can be used to perform
 * thread-safe operations without explicit locking, leveraging CAS
 * (Compare-And-Swap) for better performance under concurrent conditions.
 *
 * This demo is useful for understanding lock-free programming patterns
 * and common use cases such as counters, state management, and
 * high-performance concurrent updates.
 */

public class AtomicDemo {


    public static void main(String[] args) throws Exception {

        basicTypesDemo();
        arrayDemo();
        referenceDemo();
        fieldUpdaterDemo();
        adderDemo();
    }

    /**
     * 1. Basic Types: AtomicInteger / AtomicLong / AtomicBoolean
     */
    public static void basicTypesDemo() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);

        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                count.incrementAndGet();
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println("AtomicInteger result: " + count.get());
    }

    /**
     * 2. Array Types: AtomicIntegerArray
     */
    public static void arrayDemo() {
        AtomicIntegerArray array = new AtomicIntegerArray(3);

        array.set(0, 10);
        array.addAndGet(0, 5);

        System.out.println("AtomicIntegerArray index0: " + array.get(0));
    }

    /**
     * 3. Reference Types: AtomicReference / AtomicStampedReference
     */
    public static void referenceDemo() {
        AtomicReference<String> ref = new AtomicReference<>("A");

        ref.compareAndSet("A", "B");
        System.out.println("AtomicReference: " + ref.get());

        // ABA problem solution
        AtomicStampedReference<String> stampedRef =
                new AtomicStampedReference<>("A", 1);

        int stamp = stampedRef.getStamp();

        stampedRef.compareAndSet("A", "B", stamp, stamp + 1);

        System.out.println("AtomicStampedReference: " + stampedRef.getReference());
    }

    /**
     * 4. Field Updater: AtomicIntegerFieldUpdater
     */
    public static void fieldUpdaterDemo() {
        User user = new User();

        AtomicIntegerFieldUpdater<User> updater =
                AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

        updater.incrementAndGet(user);

        System.out.println("FieldUpdater age: " + user.age);
    }

    static class User {
        public volatile int age = 0;
    }

    /**
     * 5. High Concurrency: LongAdder
     */
    public static void adderDemo() throws InterruptedException {
        LongAdder adder = new LongAdder();

        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                adder.increment();
                latch.countDown();
            }).start();
        }

        latch.await();

        System.out.println("LongAdder result: " + adder.sum());
    }


}

