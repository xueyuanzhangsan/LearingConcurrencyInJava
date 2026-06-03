package Unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
/**

 * UnsafeDemo demonstrates the core capabilities of the Unsafe class,
 * which provides low-level operations for memory management, atomic
 * instructions, and thread coordination in Java.
 *
 * This demo covers several key functionalities:
 * * Direct memory access (allocate/free memory, read/write values)
 * * CAS (Compare-And-Swap) operations for lock-free concurrency
 * * Object field manipulation using memory offsets
 * * Creating objects without invoking constructors
 * * Thread parking and unparking for synchronization control
 *
 * These operations form the foundation of many high-level concurrency
 * utilities in the JDK, such as Atomic classes and AQS-based synchronizers.
 *
 * This example is intended for educational purposes to illustrate how
 * low-level mechanisms work under the hood, and should not be used
 * directly in production code due to safety and portability concerns.
 */

public class UnsafeDemo {

    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {

        memoryDemo();
        casDemo();
        objectFieldDemo();
        allocateInstanceDemo();
        parkUnparkDemo();
    }

    /**
     * 1. Direct Memory Operations (like malloc/free)
     */
    public static void memoryDemo() {
        long address = unsafe.allocateMemory(8);

        unsafe.putLong(address, 123456L);
        long value = unsafe.getLong(address);

        System.out.println("Memory value: " + value);

        unsafe.freeMemory(address);
    }

    /**
     * 2. CAS Operation (core of Atomic)
     */
    public static void casDemo() throws Exception {
        Counter counter = new Counter();

        Field field = Counter.class.getDeclaredField("value");
        long offset = unsafe.objectFieldOffset(field);

        boolean success = unsafe.compareAndSwapInt(counter, offset, 0, 1);

        System.out.println("CAS success: " + success);
        System.out.println("Counter value: " + counter.value);
    }

    static class Counter {
        volatile int value = 0;
    }

    /**
     * 3. Object Field Access
     */
    public static void objectFieldDemo() throws Exception {
        User user = new User();

        Field field = User.class.getDeclaredField("age");
        long offset = unsafe.objectFieldOffset(field);

        unsafe.putInt(user, offset, 30);

        int age = unsafe.getInt(user, offset);

        System.out.println("User age: " + age);
    }

    static class User {
        private int age = 0;
    }

    /**
     * 4. Create Object Without Constructor
     */
    public static void allocateInstanceDemo() throws InstantiationException {
        User user = (User) unsafe.allocateInstance(User.class);

        System.out.println("User created without constructor, age: " + user.age);
    }

    /**
     * 5. Thread Park / Unpark (used by LockSupport)
     */
    public static void parkUnparkDemo() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("Thread parking...");
            unsafe.park(false, 0L);
            System.out.println("Thread resumed!");
        });

        t.start();

        Thread.sleep(1000);

        System.out.println("Unparking thread...");
        unsafe.unpark(t);
    }

}

