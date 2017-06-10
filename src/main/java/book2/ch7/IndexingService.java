package book2.ch7;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-10 下午8:55.
 * Description:
 *
 * 毒丸对象的使用.
 */
public class IndexingService {
    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public IndexingService(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
        this.queue = queue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    /**
     * 等待消费者将阻塞队列中的所有对象都消费完成.
     * @throws InterruptedException
     */
    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        // 放入POISON的过程中被中断了，那么需要不断的 retry将对象放入到queue中...
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {

        }
    }

    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    // 可中断的take方法.
                    File file = queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                }
            } catch (InterruptedException e) {

            }
        }

        private void indexFile(File file) {

        }
    }


}
