package JMMCharacteristicsIssues;

public class VisibilityDemo {
    /**
     * this demo about visibility in jmm
     * if you do not use 'volatile' to embellish initFlag
     * on multithreading if a thread change the initFlag other thread can not discover it
     */
    //private volatile static boolean initFlag = false;
    private  static boolean initFlag = false;

    private  static int counter = 0;

    public static void refresh(){
        System.out.println("refresh data.......");
        initFlag = true;
        System.out.println("refresh data success.......");
    }

    public static void main(String[] args){
        Thread threadA = new Thread(()->{
            while (!initFlag){
                //System.out.println("runing");
                counter++;
            }
            System.out.println("thread：" + Thread.currentThread().getName()
                    + "discover initFlag changed");
        },"threadA");
        threadA.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadB = new Thread(()->{
            refresh();
        },"threadB");
        threadB.start();
    }
}
