package book1.ch3;

import book1.ch2.SyncDemo1;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午9:43.
 * Description:
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock
            = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void handleWrite(Lock lock, int index) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                // 读锁
//                demo.handleRead(readLock);
                // 写锁
                demo.handleRead(lock);
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                //
                demo.handleWrite(writeLock, new Random().nextInt());
//                demo.handleWrite(lock, new Random().nextInt());
            }
        };

        long start = System.currentTimeMillis();

        /*Thread[] threads = new Thread[20];
        for (int i = 0; i < 18; i++) {
            threads[i] = new Thread(readRunnable);
            threads[i].start();
            threads[i].join();
        }

        for (int i = 18; i < 20; i++) {
            threads[i] = new Thread(writeRunnable);
            threads[i].start();
            threads[i].join();
        }*/

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }

        for (int i = 18; i < 20; i++) {
            new Thread(writeRunnable).start();
        }

        System.out.println("total time:" + (System.currentTimeMillis() - start) / 1000.0);
    }


}
