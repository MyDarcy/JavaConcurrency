package book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 上午10:11.
 * Description:
 */
public class SimpleWaitNofifyDemo {
    static final Object object = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            // T1先申请object的对象锁；
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start.");
                try {
                    System.out.println(System.currentTimeMillis() + " :T1 wait for object.");
                    // 线程T1在wait()方法在执行后，会释放这个监视器(monitor)，这样其他等待在该对象上的线程就不会因为线程T的休眠而全部无法工作;
                    // T1持有object的锁，执行后释放object的锁；
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end.");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start notify T1 thread.");
                // 线程T2在notify()方法调用前，也必须先获得object的监视器(montitor),然后T2执行notify()方法尝试唤醒一个线程，这里就认为是T1了，T1被唤醒后，会尝试重新获得object的监视器 -- 就是T1在wait方法执行前持有的那个；如果暂时无法获得，T1还必须等待这个监视器；
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end");
                try {
                    // T2休眠2s;
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new T1().start();
        new T2().start();
    }
}
