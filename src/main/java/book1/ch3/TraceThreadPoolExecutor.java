package main.java.book1.ch3;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-5-21 下午3:30.
 * Description:
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("client stack trace.");
    }

    // clientStack保存着提交任务的线程的堆栈信息。
    // 将传入的task进行包装，使之能处理异常信息，
    private Runnable wrap(Runnable task, Exception clientStack, String name) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
                0, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100, i));
        }
    }

    static class DivTask implements Runnable {
        int a;int b;

        public DivTask(int a, int b) {
            this.a = a; this.b = b;
        }

        @Override
        public void run() {
            double result = a / b;
            System.out.println(result);
        }
    }
}
