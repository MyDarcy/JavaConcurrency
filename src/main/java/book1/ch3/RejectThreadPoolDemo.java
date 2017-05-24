package book1.ch3;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-5-21 下午2:37.
 * Description:
 */
public class RejectThreadPoolDemo {
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
        // corePoolSize和maxinumPoolSize都是5, 容量为10的等待队列。
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discarding...");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.submit(myTask);
            // MyTask执行需要话费100ms, 那么就会有大量的任务被直接抛弃。
            Thread.sleep(10);
        }
    }
}
