package sync;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        int taskCount = 3;

        // Initialize latch with number of tasks
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int i = 0; i < taskCount; i++) {
            int id = i;

            new Thread(() -> {
                try {
                    System.out.println("Task " + id + " started");

                    // Simulate work
                    Thread.sleep(1000 + id * 500);

                    System.out.println("Task " + id + " finished");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Decrease count
                    latch.countDown();
                }
            }).start();
        }

        System.out.println("Main thread waiting for tasks...");

        // Wait until count reaches 0
        latch.await();

        System.out.println("All tasks completed. Continue main flow.");
    }
}