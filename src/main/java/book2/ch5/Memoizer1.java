package book2.ch5;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:01.
 * Description:
 */
public class Memoizer1<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> computable;

    public Memoizer1(Computable<A, V> computable) {
        this.computable = computable;
    }

    /**
     * 整个方法同步,可能computable可能非常耗时.
     * @param arg
     * @return
     * @throws InterruptedException
     */
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
