package book2.ch5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-7 下午9:28.
 * Description:
 *
 *　生产者
 */
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
        this.queue = queue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File rFile) throws InterruptedException {
        File[] files = rFile.listFiles(fileFilter);
        for (File file : files) {
            if (file.isDirectory()) {
                crawl(file);
            } else {
                queue.put(file);
            }
        }
    }
}
