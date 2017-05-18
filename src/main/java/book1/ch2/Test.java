package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-18 上午10:47.
 * Description:
 */
public class Test {

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread() + "\t" + i);
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new MyRunnable()).start();
        new Thread(new MyRunnable()).start();

    }

}
