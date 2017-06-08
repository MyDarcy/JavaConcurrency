package book2.ch5;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:23.
 * Description:
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer3(Computable<A, V> computable) {
        this.computable = computable;
    }

    /**
     * 判断某个相应的计算是否已经开始, 如果是的话, 那么就阻塞等待返回值。
     * 如果没有开始，那么创建一个futrueTask, 放入到缓存中, 然后启动计算。
     *
     * 唯一的问题在于其if语句块仍然不是完整的，那么两个线程仍然可能会导致重复的计算。
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> vFuture = cache.get(arg);
        if (vFuture == null) {
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return computable.compute(arg);
                }
            };
            FutureTask<V> futureTask = new FutureTask<V>(callable);
            vFuture = futureTask;
            cache.put(arg, futureTask);
            futureTask.run();
        }
        try {
            return vFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
