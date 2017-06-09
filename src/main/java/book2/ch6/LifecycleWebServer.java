package book2.ch6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-9 上午9:50.
 * Description:
 */
public class LifecycleWebServer {
    private final ExecutorService es = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (!es.isShutdown()) {
            try {
                final Socket connection = serverSocket.accept();
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(connection);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!es.isShutdown()) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * 通过awaitTermination来等待ES到达终止状态，或者通过isTerminated来轮寻ES是否终止。
     * 通常在调用awaitTermination以后立即调用shutdown，产生同步的关闭ES的效果。
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        es.awaitTermination(10, TimeUnit.SECONDS);
        es.shutdown();
    }

    private void handleRequest(Socket connection) {
        // 读取数据
        // 如果请求是关闭连接的，那么关闭连接，否则，分发连接。
    }
}
