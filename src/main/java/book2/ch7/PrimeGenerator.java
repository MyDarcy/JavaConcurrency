package book2.ch7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-6-9 下午2:34.
 * Description:
 */
public class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes
            = new ArrayList<>();
    private volatile boolean cancelled;

    /**
     * 不断的轮询查看本任务是否取消了。
     */
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }
}
