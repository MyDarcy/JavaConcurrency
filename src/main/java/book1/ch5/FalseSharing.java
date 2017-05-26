package book1.ch5;

/**
 * Author by darcy
 * Date on 17-5-26 下午7:58.
 * Description:
 */
public class FalseSharing implements Runnable {
    //
    private static final int NUMBER_THREADS = 2;
    private static final long ITERATIONS = 500L * 1000L * 1000L;
    // 每个线程都会访问自己对应的longs中的元素.
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUMBER_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUMBER_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public void run() {
        long iterations = ITERATIONS + 1;
        while (1 != --iterations) {
            longs[arrayIndex].value = iterations;
        }
    }

    public static final class VolatileLong {
        // 只有value是被使用的, 其他的都是填充.防止数组中几个对象value进入了同一行。
        public volatile long value = 0L;
//        public long p1, p2, p3, p4, p5, p6, p7;
    }

}
