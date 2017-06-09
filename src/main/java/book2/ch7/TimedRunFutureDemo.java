package book2.ch7;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-9 下午9:54.
 * Description:
 */
public class TimedRunFutureDemo {
    private static final ScheduledExecutorService ses
            = Executors.newScheduledThreadPool(10);

    public static void timedRun(Runnable runnable, long timeout, TimeUnit timeUnit) throws ExecutionException {
        Future<?> future = ses.submit(runnable);
        try {
            future.get(timeout, timeUnit);
        } catch (TimeoutException e) {
            // 超时则任务取消.
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            // 异常被重新抛出,以便由调度者线程来处理任务.
            throw e;
        } finally {
            // 如果任务已经结束,那么执行取消操作也没有影响.
            // 如果任务超时, 被中断, 正常执行, 那么会被中断.
            future.cancel(true); // 参数mayInterruptIfRunning表示任务是否能够接受中断.
        }
    }
}
