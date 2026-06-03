package Executor;

import java.util.concurrent.*;
/**

 * ForkJoinPoolDemo demonstrates the usage of ForkJoinPool for
 * parallel computation using the divide-and-conquer approach.
 *
 * In this example, a large task is recursively split into smaller
 * subtasks using the fork() operation. Each subtask is executed
 * asynchronously by the ForkJoinPool. Once the subtasks complete,
 * their results are combined using the join() operation to produce
 * the final result.
 *
 * This pattern is particularly suitable for CPU-intensive tasks
 * that can be effectively divided into independent subtasks.
 *
 * ForkJoinPool uses a work-stealing algorithm, allowing idle threads
 * to "steal" tasks from busy threads, which improves CPU utilization
 * and overall throughput.
 *
 * It is important to note that ForkJoinPool is not always the best choice.
 * For simple or small-scale computations, the overhead of task splitting,
 * scheduling, and context switching may outweigh the benefits of parallelism.
 * In such cases, a single-threaded approach can be more efficient.
 *
 * Therefore, whether to use ForkJoinPool should be determined based on
 * actual workload characteristics and performance benchmarking.
 */


public class ForkJoinPoolDemo {


    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();

        SumTask task = new SumTask(1, 100);

        int result = pool.invoke(task);

        System.out.println("Final result: " + result);
    }

    /**
     * RecursiveTask: returns a result
     */
    static class SumTask extends RecursiveTask<Integer> {

        private static final int THRESHOLD = 10;

        private final int start;
        private final int end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            // If task is small enough, compute directly
            if (end - start <= THRESHOLD) {
                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }

                System.out.println(Thread.currentThread().getName() +
                        " computing: " + start + " ~ " + end);

                return sum;
            }

            // Split task into two subtasks
            int mid = (start + end) / 2;

            SumTask left = new SumTask(start, mid);
            SumTask right = new SumTask(mid + 1, end);

            // fork: execute asynchronously
            left.fork();

            // compute right directly (optimization)
            int rightResult = right.compute();

            // join: wait for left result
            int leftResult = left.join();

            return leftResult + rightResult;
        }
    }

}
