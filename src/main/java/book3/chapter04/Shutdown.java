package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 两种中断线程的机制
 * 1. 中断
 * 2. boolean变量来控制是否需要停止任务并且终止线程.
 */
public class Shutdown {
    public static void main(String[] args) throws Exception {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        // 睡眠1s, main线程来对CountThread进行中断,使得CountThread能感知中断而结束.
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();


        TimeUnit.SECONDS.sleep(1);
        two.cancel();
    }

    private static class Runner implements Runnable {
        private long             i;

        private volatile boolean on = true;

        /**
         * 利用volatile便来来控制线程终止的执行模式.
         */
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
