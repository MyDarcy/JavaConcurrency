package book3.chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd   = true;

    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        // 当前线程的优先级设置为8;
        Thread.currentThread().setPriority(8);
        System.out.println("done.");
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;

        for (Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount);
        }

    }

    static class Job implements Runnable {
        private int  priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}

/*
Job Priority : 1, Count : 22922906
Job Priority : 1, Count : 21229019
Job Priority : 1, Count : 29223718
Job Priority : 1, Count : 19306263
Job Priority : 1, Count : 17867147
Job Priority : 10, Count : 19421391
Job Priority : 10, Count : 24119682
Job Priority : 10, Count : 28852900
Job Priority : 10, Count : 26997137
Job Priority : 10, Count : 22246296

线程1~10的计数结果相近,忽视对线程优先级的设置
 */