package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author by darcy
 * Date on 17-5-28 下午7:29.
 * Description:
 */
public class CompletableFutureExceptionHandler {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> calculator(100))
                .exceptionally((ex) -> {
                    System.out.println(ex);
                    return 0;
                })
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "$$" + str + "$$")
                .thenAccept(System.out::println);
        future.get();
    }

    private static Integer calculator(int i) {
        return i / 0;
    }
}
