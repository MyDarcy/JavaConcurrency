package book2.ch7;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author by darcy
 * Date on 17-6-9 下午3:25.
 * Description:
 */
public class BrokenPrimeConsumer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    BrokenPrimeProducer producer;

    public BrokenPrimeConsumer(BlockingQueue<BigInteger> queue, BrokenPrimeProducer producer) {
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {
        try {
            while (needMorePrime()) {
                consume(queue.take());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            producer.cancel();
        } finally {
            if (!producer.isInterrupted()) {
                producer.cancel();
            }
        }
    }

    /**
     * 可以加一些逻辑...
     * @return
     */
    private boolean needMorePrime() {
        return true;
    }

    /**
     * 消费者
     */
    private static int count = 0;
    void consume(BigInteger element) {
        System.out.println(element);
        count++;
        if (count == 10) {
            throw new RuntimeException("only need 10 elements...");
        }
    }

    public static void main(String[] args) {
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(5);
        BrokenPrimeProducer producer = new BrokenPrimeProducer(queue);
        BrokenPrimeConsumer consumer = new BrokenPrimeConsumer(queue, producer);
        producer.start();
        consumer.start();
    }
}
