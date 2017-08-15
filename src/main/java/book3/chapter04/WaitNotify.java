
package book3.chapter04;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WaitNotify {
    static boolean flag = true;
    static Object  lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);

        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        public void run() {
            // 获取lock对象的monitor
            synchronized (lock) {

                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait @ "
                                           + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        // wait，释放锁
                        // WAITING状态, 将当前线程放置到对象的等待队列.
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }

                //
                System.out.println(Thread.currentThread() + " flag is false. running @ "
                                   + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        public void run() {
            // 加锁
            synchronized (lock) {
                // 获取lock的锁, 然后进行通知,通知的时候不会释放lock的锁,
                // 直到当前线程释放了锁,WaitThread才能从wait方法中返回（在获得锁之后）; --
                System.out.println(Thread.currentThread() + " hold lock. notify @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                // 将所有等待队列上的线程移动到对象的同步队列上.
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            //
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ "
                                   + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}

/*
Thread[WaitThread,5,main] flag is true. wait @ 10:58:33
Thread[NotifyThread,5,main] hold lock. notify @ 10:58:34
Thread[NotifyThread,5,main] hold lock again. sleep @ 10:58:39
Thread[WaitThread,5,main] flag is false. running @ 10:58:44
 */