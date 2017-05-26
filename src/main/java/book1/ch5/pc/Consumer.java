package book1.ch5.pc;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-5-26 下午3:45.
 * Description:
 */
public class Consumer implements Runnable {
    private volatile boolean isRunning;
    private BlockingQueue<PCData> queue;
    private static final int SLEEP_TIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start consumer id=" + Thread.currentThread().getId());
        Random random = new Random();

        try {
            while (true) {
                PCData pcData = queue.take();
                if (null != pcData) {
                    int result = pcData.getData() * pcData.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", pcData.getData(), pcData.getData(), result));
                    Thread.sleep(random.nextInt(SLEEP_TIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
