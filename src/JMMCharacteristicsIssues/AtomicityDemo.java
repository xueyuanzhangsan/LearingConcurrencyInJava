package JMMCharacteristicsIssues;

public class AtomicityDemo {
    /**
     * this demo about jmm Atomicity
     * when have many thread to ++ the counter
     * if you do not use the synchronized
     * the counter may is not 1000
     */
    private volatile static int counter = 0;
    static Object object = new Object();

    public static void main(String[] args) {
        for (int n = 0; n < 20; n++) {
            for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(()->{
                    for (int j = 0; j < 1000; j++) {
                        //synchronized (object){
                        counter++;//in jmm have three steep to finish it --read --add -write
                        //}
                    }
                });
                thread.start();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(counter);
            counter = 0;
        }

    }
}
