package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Author by darcy
 * Date on 17-5-28 下午6:55.
 * Description:
 */
public class CompletableFutureAsyncDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造一个CompletableFuture对象。
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    // 模拟异步任务的执行。
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 100 * 100;
            }
        });
        //异步任务没有执行完成，当前线程就会阻塞等待。
        System.out.println(future.get());
    }
}
