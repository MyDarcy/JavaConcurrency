package book1.ch4;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-5-23 下午6:17.
 * Description:
 */
public class ThreadLocalOutputDemo {
    public static final int GEN_COUNT = 10000000;
    public static final int THREAD_COUNT = 4;
    static ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

    public static Random rnd = new Random(123);

    public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long> {
        private int mode = 0;

        RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom() {
            if (mode == 0) {
                return rnd;
            } else if (mode == 1) {
                return tRnd.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long start = System.currentTimeMillis();
            for (int i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " spend " + (end - start));
            return end - start;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futures = new Future[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = es.submit(new RndTask(0));
        }
        long totalTime1 = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totalTime1 += futures[i].get();
        }
        System.out.println("多线程访问同一个Random对象的耗时:" + totalTime1);
        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = es.submit(new RndTask(1));
        }
        long totalTime2 = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totalTime2 += futures[i].get();
        }
        System.out.println("多线程访问私有Random对象的耗时:" + totalTime2);

    }
}
