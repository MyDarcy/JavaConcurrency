package book2.ch14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-6-13 下午7:42.
 * Description:
 * 用锁来实现信号量.
 * ReentranLock和Semaphore有很多相似操作，都允许一定数目的线程通过．　线程达到阀门时候，可以通过，
 * 也可以阻塞，也可以失败, 都支持可中断的，不可中断的，限时的获取操作;
 *
 */
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    private final Condition permitAvailable = lock.newCondition();
    private int permits;

    public SemaphoreOnLock(int permits) {
        lock.lock();
        try {
            this.permits = permits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            if (permits <= 0) {
                permitAvailable.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            permits++;
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }


}
