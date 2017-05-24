package book1.ch4.philosopher;

/**
 * Author by darcy
 * Date on 17-5-24 上午10:19.
 * Description:
 */
public class DeadLock extends Thread {
    protected Object tool;
    static Object fork1 = new Object();
    static Object fork2 = new Object();

    public DeadLock(Object object) {
        this.tool = object;
        if (tool == fork1) {
            setName("哲学家A");
        }
        if (tool == fork2) {
            setName("哲学家B");
        }
    }

    @Override
    public void run() {
        if (tool == fork1) {
            synchronized (fork1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2) {
                    System.out.println("哲学家A 开始吃饭.");
                }
            }
        }

        if (tool == fork2) {
            synchronized (fork2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (fork1) {
                System.out.println("哲学家B 开始吃饭了.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock d1 = new DeadLock(fork1);
        DeadLock d2 = new DeadLock(fork2);
        d1.start();
        d2.start();
        Thread.sleep(100);
    }
}
