package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author by darcy
 * Date on 17-5-28 下午7:37.
 * Description:
 */
public class CompletableFutureCompose {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> calculator(50))
                // 执行结果交由下一个CompletableFuture来为完成。
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calculator(i)))
                .thenApply((str) -> "##" + str + "##")
                .thenAccept(System.out::println);
        future.get();
    }

    private static Integer calculator(Integer i) {
        return  i / 2;
    }
}
