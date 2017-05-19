package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 上午11:21.
 * Description:
 */
public class JoinDemo {
    public static volatile int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (; i < 1000000; i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread addThread = new AddThread();
        addThread.start();
        addThread.join();
        System.out.println("i=" + i);

    }
}
