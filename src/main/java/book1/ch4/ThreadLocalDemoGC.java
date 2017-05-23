package main.java.book1.ch4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-23 下午4:32.
 * Description:
 */
public class ThreadLocalDemoGC {
    static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        // 为了跟踪ThreadLocal的垃圾回收.
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };

    static volatile CountDownLatch countDownLatch = new CountDownLatch(10000);
    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){

                        // 为了跟踪SimpleDateFormat对象的垃圾回收.
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc.");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ": create SimpleDateFormat");
                }
                Date date = t1.get().parse("2017-05-22 16:21:" + (i % 60));
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        countDownLatch.await();
        System.out.println("mission completed.");
        t1 = null;
        System.gc();
        System.out.println("First GC Completed!!");
        t1 = new ThreadLocal<>();
        countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        countDownLatch.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("Second GC Completed!!");
    }
}
