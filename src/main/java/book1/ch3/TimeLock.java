package main.java.book1.ch3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午7:39.
 * Description:
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            // 线程在锁请求中, 最多等待5s, 如果超过5s还没有得到锁, 返回false, 如果成功获得锁, 返回true;
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " getLock successed.");
                // 线程获得锁以后, 就会休眠6s, 所以持有锁的时长也会长达6s,
                // 所以另一个线程在这段时间里面都是无法获取锁的, 所以请求锁失败;
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName() + " getLock failed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
    }
}
