package sync;

import java.util.concurrent.Exchanger;
/**

 * ExchangerDemo demonstrates how to use Exchanger to synchronize two threads
 * and exchange data between them at a specific synchronization point.
 *
 * In this example, each thread prepares its own data and calls the exchange()
 * method. The threads block until both parties reach the exchange point, at which
 * time their data is swapped. After the exchange, each thread continues execution
 * using the data received from the other thread.
 *
 * This pattern is commonly used in scenarios such as double-buffering,
 * producer-consumer coordination, or data validation between two threads.
 */

public class ExchangerDemo {

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String data = "Data from A";
                System.out.println("Thread A before exchange: " + data);

                String received = exchanger.exchange(data);

                System.out.println("Thread A received: " + received);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                String data = "Data from B";
                System.out.println("Thread B before exchange: " + data);

                String received = exchanger.exchange(data);

                System.out.println("Thread B received: " + received);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}