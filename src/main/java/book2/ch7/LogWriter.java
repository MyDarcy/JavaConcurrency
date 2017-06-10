package book2.ch7;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author by darcy
 * Date on 17-6-10 下午1:09.
 * Description:
 *
 * 问题在于需要提供终止日志线程的方法．
 */
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    public static final int CAPACITY = 100;

    public LogWriter(PrintWriter writer) {
        queue = new LinkedBlockingDeque<>(CAPACITY);
        logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    class LoggerThread extends Thread {
        private final PrintWriter writer;
        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // take方法能够响应中断．
                    writer.println(queue.take());
                }
            } catch (InterruptedException e) {
                // 忽略异常．此时中断了日志线程．
            } finally {
                writer.close();
            }
        }
    }
}

