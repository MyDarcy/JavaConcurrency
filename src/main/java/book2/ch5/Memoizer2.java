package book2.ch5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:01.
 * Description:
 */
public class Memoizer2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer2(Computable<A, V> computable) {
        this.computable = computable;
    }

    /**
     * 并发性更好，但是问题在于说如果一个线程发现缓存中没有对象，然后进入了计算，此时，另一个线程也判断同一个对象
     * 也不在该缓存中,也去进行计算。 那么实际上针对同一个任务的重复计算。　
     * 但是
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
