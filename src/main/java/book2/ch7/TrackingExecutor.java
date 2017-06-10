package book2.ch7;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-10 下午9:31.
 * Description:
 *
 * shutdownNow()方法强行关闭的ExecutorService时候，
 * 会尝试取消正在执行的任务同时返回已经提交但是尚未执行的任务．
 *
 * 这里需要找的是那些已经开始执行但是还没有执行结束的任务．
 */
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService es;
    private final Set<Runnable> tasksRunnableAtShutdown = Collections.synchronizedSet(
            new HashSet<>());

    public TrackingExecutor(ExecutorService executorService) {
        this.es = executorService;
    }

    // 其他方法委托给ExecutorService...
    @Override
    public void shutdown() {
        es.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return es.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return es.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return es.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return es.awaitTermination(timeout, unit);
    }

    /**
     * 哪些任务是在ExecutorService关闭之后取消的．
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    if (isShutdown()
                            && Thread.currentThread().isInterrupted()) {
                        tasksRunnableAtShutdown.add(command);
                    }
                }
            }
        });
    }

    public List<Runnable> getCancelledTasks() {
        // es还没有关闭就要获取已经取消的任务列表...
        if (!es.isTerminated()) {
            throw new IllegalStateException("no call at property time.");
        }
        return new ArrayList<>(tasksRunnableAtShutdown);
    }
}
