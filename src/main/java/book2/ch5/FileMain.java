package book2.ch5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-7 下午9:36.
 * Description:
 */
public class FileMain {
    public static void main(String[] args) {
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(10);

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        String path = "/home/darcy/IdeaProjects/JavaConcurrency";

        for (int i = 0; i < 5; i++) {
            new Thread(new FileCrawler(queue, fileFilter, new File(path))).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}
