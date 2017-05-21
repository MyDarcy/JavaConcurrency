package main.java.book1.ch3;

import java.util.concurrent.locks.LockSupport;

/**
 * Author by darcy
 * Date on 17-5-20 上午11:25.
 * Description:
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            t1.start();
            Thread.sleep(1000);
            t2.start();

            // 仍然不能保证unpack方法发生在pack方法之后,
            LockSupport.unpark(t1);
            LockSupport.unpark(t2);
            t1.join();
            t2.join();
        }
    }

}
