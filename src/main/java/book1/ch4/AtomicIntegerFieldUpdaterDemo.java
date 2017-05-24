package book1.ch4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Author by darcy
 * Date on 17-5-23 下午8:58.
 * Description:
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static class Candidate {
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdate
            = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate candidate = new Candidate();
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i]= new Thread() {
                @Override
                public void run() {
                    if (Math.random() > 0.4) {
                        scoreUpdate.incrementAndGet(candidate);
                        atomicInteger.incrementAndGet();
                    }
                }
            };
            threads[i].start();
        }

        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }
        System.out.println("score:" + candidate.score);
        System.out.println("atomicScore:" + atomicInteger.get());
    }
}
