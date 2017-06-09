package book2.ch7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-9 下午9:39.
 * Description:
 */
public class TimedRunBetterDemo {

    private static final ScheduledExecutorService ses
            = Executors.newScheduledThreadPool(10);

    public static void timedRun(final Runnable task, long timeout, TimeUnit unit) throws InterruptedException {


        class RethrowableTask implements Runnable {
            // throwable在两个线程之间共享.所以变量在两个线程之间安全的发布.
            private volatile Throwable throwable;
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Throwable throwable) {
                    this.throwable = throwable;
                }
            }

            void rethrow() {
                if (throwable != null) {
                    throw new RuntimeException(throwable);
                }
            }
        }

        RethrowableTask rethrowableTask = new RethrowableTask();
        final Thread thread = new Thread(rethrowableTask);
        thread.start();
        ses.schedule(new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        }, timeout, unit);

        // 等待thread 指定的时长.
        thread.join(unit.toMillis(timeout));
        // 抛出的线程直接由调度者线程进处理.
        rethrowableTask.rethrow();
    }

}
