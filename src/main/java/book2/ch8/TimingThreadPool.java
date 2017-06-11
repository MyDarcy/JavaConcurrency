package book2.ch8;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Author by darcy
 * Date on 17-6-11 下午2:35.
 * Description:
 */
public class TimingThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger logger = Logger.getLogger("TimingThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 执行任务的线程中调用这些方法...
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.fine(String.format("Thread %s: start %s", r, t));
        startTime.set(System.nanoTime());
    }

    /**
     * 执行任务的线程中调用这些方法...
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.fine(String.format("Thread %s: end %s, time=%s", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            logger.info(String.format("Terminated, avg time=%s, numberTasks=%s", totalTime.get() / numTasks.get(), numTasks.get()));
        } finally {
            super.terminated();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimingThreadPool timingThreadPool = new TimingThreadPool(10, 10, 5000, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        for (int i = 0; i < 10; i++) {
            timingThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        timingThreadPool.shutdown();
        timingThreadPool.awaitTermination(5000, TimeUnit.SECONDS);
    }
}
