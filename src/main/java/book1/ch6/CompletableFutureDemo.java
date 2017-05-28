package book1.ch6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Author by darcy
 * Date on 17-5-28 下午5:11.
 * Description:
 */
public class CompletableFutureDemo implements Runnable {
    CompletableFuture<Integer> result = null;

    public CompletableFutureDemo(CompletableFuture<Integer> result) {
        this.result = result;
    }
    @Override
    public void run() {
        int value = 0;
        try {
            // 阻塞, 因为future中没有数据。
            value = result.get() * result.get();
        } catch (Exception e) {

        }
        System.out.println(value);
    }

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new CompletableFutureDemo(future)).start();
        // 模拟主线程的业务逻辑处理.
        Thread.sleep(1000);
        // 将最终数据载入future, 并标记完成状态。
        // 执行完后，那么Future线程就可以继续执行。
        future.complete(60);
    }
}
