package book2.ch7;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-10 下午6:55.
 * Description:
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private final PrintWriter writer;

    public LogService(PrintWriter writer) {
        this.writer = writer;
        logger = new LoggerThread();
        queue = new LinkedBlockingQueue<>(10);
    }

    private boolean isShutdown;
    private int reservations;


    public void start() {
        logger.start();
    }

    public void stop() {
        synchronized (LogService.class) {
            isShutdown = true;
        }
        logger.interrupt();
    }

    /**
     * 日志消息的提交作为一个原子操作，但是因为put本身就是阻塞的，所以是通过原子的方式来检查关闭操作.
     * 并且通过一个计数器来维护提交消息的个数．
     * @param msg
     * @throws InterruptedException
     */
    public void log(String msg) throws InterruptedException {
        synchronized (LogService.class) {
            if (isShutdown) {
                throw new IllegalStateException("Thread shutdown...");
            }
            reservations++;
        }
        queue.put(msg);
    }

    class LoggerThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.class) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (LogService.class) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        // retry...
                        // 有消息未处理完毕，那么线程被中断时，重试．
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}

