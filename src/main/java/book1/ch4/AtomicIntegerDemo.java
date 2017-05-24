package book1.ch4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by darcy
 * Date on 17-5-23 下午7:37.
 * Description:
 */
public class AtomicIntegerDemo {
    static AtomicInteger atomicInteger = new AtomicInteger();

    public static class AddTask implements Runnable {

        public void run() {
            for (int i = 0; i < 10000; i++) {
                atomicInteger.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddTask());
        }

        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }

        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
        System.out.println(atomicInteger.get());
    }

}
