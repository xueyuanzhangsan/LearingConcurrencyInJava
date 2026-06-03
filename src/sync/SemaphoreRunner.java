package sync;

import java.util.concurrent.Semaphore;

/**

 * SemaphoreRunner demonstrates how to use a Semaphore to control
 * the number of concurrent threads accessing a shared resource.
 *
 * In this example, a fixed number of permits is defined, allowing only
 * a limited number of threads to enter the critical section at the same time.
 * Additional threads will block until a permit becomes available.
 *
 * Each task simulates a unit of work by acquiring a permit, performing
 * a time-consuming operation, and then releasing the permit for others.
 *
 * This pattern is commonly used for resource management, such as limiting
 * access to database connections, external services, or controlling
 * overall system concurrency.
 */

public class SemaphoreRunner {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i=0;i<5;i++){
            new Thread(new Task(semaphore,"yangguo+"+i)).start();
        }
    }

    static class Task extends Thread{
        Semaphore semaphore;

        public Task(Semaphore semaphore,String tname){
            this.semaphore = semaphore;
            this.setName(tname);
        }

        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+":aquire() at time:"+System.currentTimeMillis());
                Thread.sleep(1000);
                semaphore.release();
                System.out.println(Thread.currentThread().getName()+":aquire() at time:"+System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}