package book1.ch3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午8:06.
 * Description:
 */
public class FairLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " get Lock.");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock, "Thread-1");
        Thread t2 = new Thread(fairLock, "Thread-2");
        t1.start(); t2.start();
    }
}
