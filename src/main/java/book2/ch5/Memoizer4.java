package book2.ch5;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:33.
 * Description:
 */
public class Memoizer4<A, V> implements Computable<A, V > {
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer4(Computable<A, V> computable) {
        this.computable = computable;
    }

    /**
     * 此方法虽然可以避免３中的问题, 不可能针对同一个参数任务放入两次。
     *
     * 但是为了避免缓存污染的问题,即在计算结果时候, 指明计算过程被取消或者失败，为了避免这种情况，那么就需要在
     * 发现任务被取消的时候，将Future从缓存中移除。同时，抛出RuntimeException异常的时候，也移除Future。
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> vFuture = cache.get(arg);
            if (vFuture == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(arg);
                    }
                };
                FutureTask<V> futureTask = new FutureTask<V>(callable);
                vFuture = cache.putIfAbsent(arg, futureTask);
                if (vFuture == null) {
                    vFuture = futureTask;
                    futureTask.run();
                }
            }
            try {
                vFuture.get();
            } catch (CancellationException e) {
                cache.remove(arg, vFuture);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
