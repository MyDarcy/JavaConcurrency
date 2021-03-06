package book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 下午3:06.
 * Description:
 */
public class SyncDemo1 implements Runnable {

    static SyncDemo1 demo1 = new SyncDemo1();
    static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {

            // 这个代码块等价于increase方法,
            synchronized (demo1) {
                i++;
            }
        }
    }

    private synchronized void increase() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        // 注意这里, 使用Runnable实例创建两个线程, 这样才能保证两个线程在工作时候，关注到同一个对象锁上去，从而保证线程安全。
        Thread t1 = new Thread(demo1);
        Thread t2 = new Thread(demo1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
