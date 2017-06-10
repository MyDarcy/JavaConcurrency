package book2.ch7;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-10 下午9:58.
 * Description:
 */
public abstract class WebCrawler {
    private volatile TrackingExecutor executor;
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        executor = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    /**
     * 服务关闭的时候无论是没有开始的任务还是被取消的任务，都记录在待处理的URL集合中...
     * @throws InterruptedException
     */
    public synchronized void stop() throws InterruptedException {
        try {
            saveUncrawled(executor.shutdownNow());
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                saveUncrawled(executor.getCancelledTasks());
            }
        } finally {
            executor = null;
        }
    }

    private void saveUncrawled(List<Runnable> runnables) {
        for (Runnable task : runnables) {
            urlsToCrawl.add(((CrawlTask) task).getPage());
        }
    }

    private void submitCrawlTask(URL url) {
        executor.execute(new CrawlTask(url));
    }

    class CrawlTask implements Runnable {
        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            for (URL innerUrl : processUrl(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(url);
            }
        }

        public URL getPage() {
            return url;
        }
    }

    protected abstract List<URL> processUrl(URL url);
}
