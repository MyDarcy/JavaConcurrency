package main.java.book1.ch3;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-5-21 下午2:54.
 * Description:
 */
public class ThreadFactoryDemo {
    static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread();
                        t.setDaemon(true);
                        System.out.println("create:" + t);
                        return t;
                    }
                }
        );
        for (int i = 0; i < 5; i++) {
            es.submit(myTask);
        }
        Thread.sleep(2000);
    }
}
