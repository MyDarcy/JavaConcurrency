package book2.ch5;

import java.util.concurrent.CountDownLatch;

/**
 * Author by darcy
 * Date on 17-6-7 下午10:44.
 * Description:
 */
public class CountDownLatchDemo {

    public long timeTasks(int threadNumber, Runnable task) throws InterruptedException {

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            final int number = i;
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            System.out.println(number);
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.currentTimeMillis();
        // 主线程通过startGate.countDown()来同时释放所有的工作线程。
        startGate.countDown();
        // 等待所有的线程执行完毕。
        endGate.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        CountDownLatchDemo demo = new CountDownLatchDemo();
        int threadNumber = 20;
        long time = demo.timeTasks(threadNumber, task);
        System.out.println(time);
    }

}
