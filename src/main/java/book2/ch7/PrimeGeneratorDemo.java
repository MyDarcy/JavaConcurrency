package book2.ch7;

import java.math.BigInteger;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Author by darcy
 * Date on 17-6-9 下午2:39.
 * Description:
 */
public class PrimeGeneratorDemo {

    public static void main(String[] args) throws InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        new Thread(primeGenerator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            primeGenerator.cancel();
        }
        List<BigInteger> result = primeGenerator.get();
        System.out.println(result);
    }

}
