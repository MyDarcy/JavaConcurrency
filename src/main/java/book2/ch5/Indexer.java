package book2.ch5;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-7 下午9:34.
 * Description:
 *
 * 消费者.
 */
public class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
