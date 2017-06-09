package book2.ch7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-9 下午9:31.
 * Description:
 */
public class TimedTaskDemo {

    private static final ScheduledExecutorService ses
            = Executors.newScheduledThreadPool(10);

    public static void timedRun(Runnable task, long timeout, TimeUnit unit) {
        final Thread thread = Thread.currentThread();
        ses.schedule(new Runnable() {
            @Override
            public void run() {
                // 给定的延时之后中断调度线程.
                thread.interrupt();
            }
            // 给定的延时之后执行任务.
        }, timeout, unit);

        // 调用者线程中执行任务.
        // 此时task抛出的异常会被调度线程中被捕获.
        task.run();
    }
}
