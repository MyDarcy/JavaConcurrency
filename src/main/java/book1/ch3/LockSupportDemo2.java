package main.java.book1.ch3;

import java.util.concurrent.locks.LockSupport;

/**
 * Author by darcy
 * Date on 17-5-20 上午11:25.
 * Description:
 */
public class LockSupportDemo2 {
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
                if (Thread.interrupted()) {
                    System.out.println(getName() + " 被中断了.");
                }
            }
            System.out.println(getName() + " finish.");
        }

        public static void main(String[] args) throws InterruptedException {
            t1.start();
            Thread.sleep(1000);
            t2.start();

             // 中断了处于pack状态的线程t1, 而且t1也可以马上响应这个中断并且立即返回，然后外面等待的t2才可以进入临界区.
            t1.interrupt();
            // t2进入理解区也阻塞在了pack()方法处,此时通过unpack()方法使其运行接收.
            LockSupport.unpark(t2);

        }
    }

}
