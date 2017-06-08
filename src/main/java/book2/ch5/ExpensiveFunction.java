package book2.ch5;

import java.math.BigInteger;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:00.
 * Description:
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        Thread.sleep(1000);
        return new BigInteger(arg);
    }
}
