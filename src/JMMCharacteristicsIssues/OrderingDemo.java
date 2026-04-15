package JMMCharacteristicsIssues;

public class OrderingDemo {
    /**
     *  jmm Ordering have some hard to prove it
     *  we have four int value is 0
     *  we change the value a b is 1
     *  whether thread T1 faster then T2 or T2 faster T1
     *  the x=1 y=0 or x=0  y=1
     *  but if the cpu have Instruction Reordering
     *  x=b
     *  a=1
     *  the value will be 0 0
     *  we can use volatile to stop the Instruction Reordering
     *  The core principle is Memory Barrier
     *  You can use other way to use Memory Barrier like Unsafe
     */
    private  static int x = 0, y = 0;
   // private volatile  static int x = 0, y = 0;
    private  static int a = 0, b = 0;
    //private volatile static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;){
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    shortWait(10000);
                    a = 1;
                    x = b;
                }
            });

            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            String result = "on" + i + "time (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }

    }

    /**
     * wait for some time
     * @param interval
     */
    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
