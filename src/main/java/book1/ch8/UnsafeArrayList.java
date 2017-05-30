package book1.ch8;

import java.util.ArrayList;

/**
 * Author by darcy
 * Date on 17-5-29 下午4:22.
 * Description:
 */
public class UnsafeArrayList {
    static ArrayList list = new ArrayList();

    static class AddTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1000000; i++) {
                // ((!(Thread.currentThread().getName().equals("main"))) && size == 9)
                list.add(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new AddTask(), "t1");
        Thread thread2 = new Thread(new AddTask(), "t2");
        thread1.start();
        thread2.start();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t3");
        thread3.start();
    }
}
