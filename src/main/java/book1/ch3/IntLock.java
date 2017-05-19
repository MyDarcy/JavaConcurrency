package main.java.book1.ch3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-5-19 下午7:22.
 * Description:
 */
public class IntLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            // 两个不同的线程t1, t2以不同的顺序请求获取锁对象lock1和 lock2.
            // 顺序相反刚好,所以很容易形成线程t1和t2之间的相互等待.
            if (lock == 1) {

                // 对锁的请求，统一使用lockInterruptibly(),
                // 这是一个可以对中断进行响应的锁申请动作, 即在等待锁的过程中, 可以响应中断.
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + ": 线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start(); t2.start();
        Thread.sleep(1000);
        // 中断线程2; t2会放弃对lock1的申请, 同时释放已经获得的lock2;
        t2.interrupt();

    }
}
