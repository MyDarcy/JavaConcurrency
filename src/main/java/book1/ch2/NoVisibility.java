package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 下午2:12.
 * Description:
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 31;
        ready = true;
        Thread.sleep(10000);
    }

}
