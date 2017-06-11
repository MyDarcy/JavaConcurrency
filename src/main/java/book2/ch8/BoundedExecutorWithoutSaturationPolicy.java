package book2.ch8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * Author by darcy
 * Date on 17-6-11 上午11:51.
 * Description:
 *
 * 当工作队列满而没有预定义的饱和策略来阻塞execute...
 * 可以通过使用Semaphore来限制任务的到达率...
 * 本例子中ExecutorService使用的是无界队列, 并设置信号量的上界设置为(线程池的大小加上可排队任务的数量)...
 */
public class BoundedExecutorWithoutSaturationPolicy {
    private final ExecutorService es;

    private final Semaphore semaphore;

    public BoundedExecutorWithoutSaturationPolicy(ExecutorService es, int semaphoreNumber) {
        this.es = es;
        this.semaphore = new Semaphore(semaphoreNumber);
    }

    public void submitTask(final Runnable task) throws InterruptedException {
        semaphore.acquire();
        try {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }


}
