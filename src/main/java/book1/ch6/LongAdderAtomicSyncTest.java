package book1.ch6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Author by darcy
 * Date on 17-5-28 下午8:53.
 * Description:
 */
public class LongAdderAtomicSyncTest {
    private static final int MAX_THREADS = 3;
    private static final int TASK_COUNT = 3;
    private static final int TARGET_COUNT = 10000000;

    private AtomicLong atomicLong = new AtomicLong(0L);
    private LongAdder longAdder = new LongAdder();
    private long normalNumber = 0;

    static CountDownLatch atomicCountDownLatch = new CountDownLatch(TASK_COUNT);
    static CountDownLatch longAdderCountDownLatch = new CountDownLatch(TASK_COUNT);
    static CountDownLatch normalCountDownLatch = new CountDownLatch(TASK_COUNT);


    private synchronized long incNormalNumber() {
        return ++normalNumber;
    }

    private synchronized long getNormalNumber() {
        return normalNumber;
    }

    class SyncTask implements Runnable {
        long start;

        public SyncTask(long start) {
            this.start = start;
        }
        @Override
        public void run() {
            long count = getNormalNumber();
            while (count < TARGET_COUNT) {
                count = incNormalNumber();
            }
            long end = System.currentTimeMillis();
            System.out.println("SyncTask Time:" + (end - start) + "ms v=" + count );
            normalCountDownLatch.countDown();
        }
    }

    class AtomicTask implements Runnable {
        long start;

        public AtomicTask(long start) {
            this.start = start;
        }
        @Override
        public void run() {
            long value = atomicLong.get();
            while (value < TARGET_COUNT) {
                value = atomicLong.incrementAndGet();
            }
            long end = System.currentTimeMillis();
            System.out.println("AtomicTask Time:" + (end - start) + "ms v=" + value);
            atomicCountDownLatch.countDown();
        }
    }

    class LongAdderTask implements Runnable {
        long start;

        public LongAdderTask(long start) {
            this.start = start;
        }
        @Override
        public void run() {
            long sum = longAdder.sum();
            while (sum < TARGET_COUNT) {
                longAdder.increment();
                sum = longAdder.sum();
            }
            long end = System.currentTimeMillis();
            System.out.println("LongAdderTask Time:" + (end - start) + "ms v=" + sum);
            longAdderCountDownLatch.countDown();
        }
    }

    public void testSync() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
        long start = System.currentTimeMillis();
        SyncTask syncTask = new SyncTask(start);
        for (int i = 0; i < TASK_COUNT; i++) {
            es.execute(syncTask);
        }
        normalCountDownLatch.await();
        es.shutdown();
    }

    public void testAtomic() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
        long start = System.currentTimeMillis();
        AtomicTask atomicTask = new AtomicTask(start);
        for (int i = 0; i < TASK_COUNT; i++) {
            es.execute(atomicTask);
        }
        atomicCountDownLatch.await();
        es.shutdown();
    }

    public void testLongAdder() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
        long start = System.currentTimeMillis();
        LongAdderTask longAdderTask = new LongAdderTask(start);
        for (int i = 0; i < TASK_COUNT; i++) {
            es.execute(longAdderTask);
        }
        longAdderCountDownLatch.await();
        es.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdderAtomicSyncTest test = new LongAdderAtomicSyncTest();
        test.testSync();
        test.testAtomic();
        test.testLongAdder();
    }
}
