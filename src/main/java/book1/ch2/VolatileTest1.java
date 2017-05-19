package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 下午2:06.
 * Description:
 */
public class VolatileTest1 {
    static volatile int i = 0;

    public static class PlusTask implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < threads.length; j++) {
            threads[j] = new Thread(new PlusTask());
            threads[j].start();
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }

        System.out.println(i);
    }

}
