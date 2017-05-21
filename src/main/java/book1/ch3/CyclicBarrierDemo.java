package main.java.book1.ch3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Author by darcy
 * Date on 17-5-20 上午10:21.
 * Description:
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                // 等待10个线程都执行到这里; 士兵到齐；
                cyclicBarrier.await();
                //
                doWork();
                // 每个士兵完成任务，都要求下一轮计数; 等待10个线程都执行到这里，即进行下一次计数; 士兵完成工作；
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + " : job done.");
        }
    }

    public static class BarrierRunnable implements Runnable {
        boolean flag;
        int N;

        public BarrierRunnable(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        // 一旦一次计数任务完成（N个）, 就会执行这里。
        @Override
        public void run() {
            if (flag) {
                System.out.println("Commander:[Soldier" + N + ", job done.");
            } else {
                System.out.println("Commander:[Soldier" + N + ", collection done.");
                flag = true;
            }
        }

        public static void main(String[] args) {
            int N = 10;
            Thread[] threads = new Thread[N];
            boolean flag = false;

            // 创建CyclicBarrier并且将计数器计为10; 计数器达到指标时(10个), 执行BarrierRunnable任务;
            CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRunnable(flag, N));
            System.out.println("troop collection.");
            for (int i = 0; i < N; i++) {
                System.out.println("Soldier" + i + " Report.");
                threads[i] = new Thread(new Soldier("Soldier " + i, cyclicBarrier));
                threads[i].start();
                if (i == 5) {
                    threads[i].interrupt();
                }
            }
        }
    }

}
