package main.java.book1.ch3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午8:58.
 * Description:
 */
public class ReentrantLockCondtion implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            // 当前线程等待，直到被唤醒。
            condition.await();
            System.out.println("Thread is going on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondtion reentrantLockCondtion = new ReentrantLockCondtion();
        Thread t1 = new Thread(reentrantLockCondtion);
        t1.start();
        Thread.sleep(1000);
        lock.lock();
        // 通知线程t1继续执行。
        condition.signal();
        // signal方法调用之后，需要释放相关的锁，以使得被唤醒的进行可以继续执行。
        // 如果本行注释，那么虽然唤醒了线程t1,但是t1并没有重新获得锁，所以也就无法继续执行。
        lock.unlock();

    }
}
