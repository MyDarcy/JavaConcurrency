package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author by darcy
 * Date on 17-5-28 下午7:37.
 * Description:
 */
public class CompletableFutureCompose2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> calculator(50));
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(() -> calculator(100));

        // 首先完成当前future和future2的执行, 然后去执行BiFunction。
        future.thenCombine(future2, (x, y) -> x + y)
                .thenApply((str) -> "$$" + str + "$$")
                .thenAccept(System.out::println);

        future.get();
    }

    private static Integer calculator(Integer i) {
        return  i / 2;
    }
}
