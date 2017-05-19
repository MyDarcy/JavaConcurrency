package main.java.book1.ch3;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-19 下午10:43.
 * Description:
 */
public class CountDownLatchDemo implements Runnable{
    // 需要完成10个任务, 等待在该countDownLatch的线程才能继续执行。
    static final CountDownLatch countDownLatch = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();


    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5) * 1000);
            System.out.println("check completed.");
            // 当前线程完成任务, 计数器-1;
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(demo);
        }
        // 要求主线程等待所有10个任务全部完成，主线程才能继续执行。
        countDownLatch.await();

        System.out.println("Fire.");
        executorService.shutdown();
    }
}
