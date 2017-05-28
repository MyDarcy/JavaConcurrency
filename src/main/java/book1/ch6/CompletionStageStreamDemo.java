package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author by darcy
 * Date on 17-5-28 下午7:21.
 * Description:
 */
public class CompletionStageStreamDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calculate(100))
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "$$" + str + "$$")
                .thenAccept(System.out::println);
        // 调用get()方法等待, 否则主线程就直接结束了, Daemon线程就全部结束了。
        future.get();
    }

    private static Integer calculate(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * i;
    }
}
