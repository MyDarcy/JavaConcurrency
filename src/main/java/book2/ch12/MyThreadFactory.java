package book2.ch12;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by darcy
 * Date on 17-6-12 下午2:54.
 * Description:
 */
public class MyThreadFactory implements ThreadFactory {
    public final AtomicInteger threadNumber = new AtomicInteger(0);
    private final ThreadFactory threadFactory
            = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        threadNumber.incrementAndGet();
        return threadFactory.newThread(r);
    }

}
