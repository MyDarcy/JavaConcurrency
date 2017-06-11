package book2.ch8;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-11 上午10:47.
 * Description:
 */
public class ThreadDeadLock {
    ExecutorService es = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = es.submit(new LoadFileTask("header.html"));
            footer = es.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // 死锁,因为本线程在等待子线程的结果,而线程池是单个线程的.
            return header.get() + footer.get();
        }
    }

    private String renderBody() {
        return null;
    }

    public class LoadFileTask implements Callable {

        String url;

        public LoadFileTask(String url) {
            this.url = url;
        }

        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
