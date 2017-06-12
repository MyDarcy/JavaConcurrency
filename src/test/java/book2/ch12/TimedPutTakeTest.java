package book2.ch12;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by darcy
 * Date on 17-6-12 下午2:13.
 * Description:
 */
public class TimedPutTakeTest {
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> boundedBuffer;
    private final int nTrials, nPairs;
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    private final BarrierTimer timer = new BarrierTimer();

    public TimedPutTakeTest(int capacity, int nPairs, int nTrials) {
        this.barrier = new CyclicBarrier(nPairs * 2 + 1);
        this.boundedBuffer = new BoundedBuffer(capacity);
        this.nTrials = nTrials;
        this.nPairs = nPairs;
    }

    public void test() {
        timer.clear();
        try {
            for (int i = 0; i < nPairs; i++) {
                exec.execute(new Producer());
                exec.execute(new Consumer());
            }
            // 调用,阻塞,直到有2*nPairs个线程被阻塞了.
            barrier.await();
            // 直到有2*nPairs个线程被阻塞了.
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * nTrials);
            System.out.println("Time per item:" + nsPerItem + " ns");
            System.out.println(putSum.get() + "\t" + takeSum.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TimedPutTakeTest demo = new TimedPutTakeTest(10, 10, 100000);
        demo.test();
        exec.shutdown();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                int seed = (hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = 0; i < nTrials; i++) {
                    boundedBuffer.put(seed);
                    sum += seed;
                    seed = xOrShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                int sum = 0;
                // 调用就阻塞...
                barrier.await();
                for (int i = 0; i < nTrials; i++) {
                    sum += boundedBuffer.take();
                }
                takeSum.getAndAdd(sum);
                // 调用就阻塞.
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int xOrShift(int seed) {
        seed ^= (seed << 6);
        seed ^= (seed >>> 21);
        seed ^= (seed << 7);
        return seed;
    }

    class BarrierTimer implements Runnable {
        private boolean started;
        private long startTime, endTime;

        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if (!started) {
                started = true;
                startTime = t;
            } else {
                endTime = t;
            }
        }

        public synchronized void clear() {
            started = false;
        }

        public synchronized long getTime() {
            return endTime - startTime;
        }
    }

}
