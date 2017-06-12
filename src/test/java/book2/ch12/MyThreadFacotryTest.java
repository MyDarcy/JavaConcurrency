package book2.ch12;

import junit.framework.TestCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-6-12 下午2:59.
 * Description:
 */
public class MyThreadFacotryTest extends TestCase {

    public void testPoolExpansion() throws InterruptedException {
        final int MAX_SIZE = 10;
        MyThreadFactory factory = new MyThreadFactory();
        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, factory);
        for (int i = 0; i < 10 * MAX_SIZE; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (int i = 0; i < 20 && factory.threadNumber.get() < MAX_SIZE; i++) {
            Thread.sleep(100);
        }
        assertEquals(factory.threadNumber.get(), MAX_SIZE);
        exec.shutdownNow();
    }
}
