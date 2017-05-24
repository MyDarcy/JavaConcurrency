package book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 上午10:54.
 * Description:
 */
public class GoodSuspendResume {
    public static Object object = new Object();

    public static class ChangeObjectThread extends Thread {
        // 表示当前线程是否被挂起；
        volatile boolean suspendMe = false;

        // 挂起线程；
        public void suspendMe() {
            this.suspendMe = true;
        }

        // 继续执行线程；
        public void resumeMe() {
            this.suspendMe = false;
            // 清除挂起标志，发notify通知；
            synchronized (this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                // 处于挂起状态是要一直处于取得对象锁的状态；
                synchronized (this) {
                    // 线程先检查自己是否挂起，如果是，执行wait()方法进行等待，否则进行正常的处理；
                    while (suspendMe) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                synchronized (object) {
                    System.out.println("In ChangeObjectThread.");
                }
            }
        }
    }


    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (object) {
                    System.out.println("In ReadObjectThread.");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 2s");
        Thread.sleep(2000);
        System.out.println("resume t1");
        // 线程t1得到继续执行的notify通知，并且会清除挂起标志；从而正常执行；
        t1.resumeMe();
    }
}

