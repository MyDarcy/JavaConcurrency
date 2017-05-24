package book1.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Author by darcy
 * Date on 17-5-19 下午9:30.
 * Description:
 */
public class SemaphoreDemo implements Runnable {
    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            // 申请信号量，
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ":done.");
            // 释放信号量
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemaphoreDemo demo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            executorService.submit(demo);
        }
    }
}
