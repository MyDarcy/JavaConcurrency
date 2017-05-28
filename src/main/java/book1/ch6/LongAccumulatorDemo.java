package book1.ch6;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * Author by darcy
 * Date on 17-5-28 下午9:33.
 * Description:
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator longAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
        ExecutorService es = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            es.submit(() ->{
                Random random = new Random();
                longAccumulator.accumulate(random.nextLong());
            });
        }
        es.shutdown();
        System.out.println(longAccumulator.longValue());
    }
}
