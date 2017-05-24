package book1.ch3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-5-21 下午3:04.
 * Description:
 */
public class ExtendThreadPool {
    static class MyTask implements Runnable {

        String name;

        MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println( "Executing: Thread ID:" + Thread.currentThread().getId() + "\t ThreadName:" + name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("prepare to execute:" + ((MyTask)r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("completed execute:" + ((MyTask) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("thread pool exit.");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask task = new MyTask("TASK-" + i);
            es.execute(task);
            Thread.sleep(10);
        }
        es.shutdown();
    }
}
