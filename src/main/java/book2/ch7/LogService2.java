package book2.ch7;

import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-10 下午8:45.
 * Description:
 *
 * 通常的做法是将ExecutorService封装到某个更高级别的服务中，该服务提供了自己的声明周期方法．
 */
public class LogService2 {
    private final BlockingQueue<String> queue;
    private final PrintWriter writer;
    private final ExecutorService es = Executors.newSingleThreadExecutor();

    public LogService2(PrintWriter writer) {
        this.writer = writer;
        queue = new LinkedBlockingQueue<>(10);
    }

    public void start() {

    }

    public void stop() throws InterruptedException {
        try {
            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

}
