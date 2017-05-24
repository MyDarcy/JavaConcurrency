package book1.ch3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午4:58.
 * Description:
 */
public class ReentrantLockDemo implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            // 获取锁
            lock.lock();
            try {
                i++;
            } finally {
                // 释放锁;
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        Thread t1 = new Thread(reentrantLockDemo);
        Thread t2 = new Thread(reentrantLockDemo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
