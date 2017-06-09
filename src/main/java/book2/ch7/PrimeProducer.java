package book2.ch7;

import scala.math.BigInt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-9 下午4:33.
 * Description:
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
           // 屏蔽中断，因为知道线程将要结束，调用栈上层没有代码需要知道中断信息。
        }
    }

    public void cancel() {
        interrupt();
    }
}
