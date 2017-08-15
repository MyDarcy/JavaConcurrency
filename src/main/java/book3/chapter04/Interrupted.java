package book3.chapter04;

import java.util.concurrent.TimeUnit;

public class Interrupted {

    public static void main(String[] args) throws Exception {

        /**
         * Sleep
         */
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);

        /**
         * Running
         */
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();

        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        /**
         * SleepThread interrupted is false
         * BusyThread interrupted is true
         */
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted()); // 一直运行的线程，中断位并没有被清除
        /**
         * 防止两个线程立即退出
         */
        TimeUnit.SECONDS.sleep(2);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
