package book1.ch5.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Author by darcy
 * Date on 17-5-27 下午9:28.
 * Description:
 */
public class AIOServer {
    public static final int PORT = 8000;
    private AsynchronousServerSocketChannel serverSocketChannel;

    public AIOServer() throws IOException {
        serverSocketChannel =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    /**
     * 开启服务器
     */
    public void start() {
        System.out.println("Server listening on " + PORT);
        // 只是调用了一个accept方法。
        // accept方法会立即返回, 并不会真的等待client的到来
        // 第一个参数是让当前线程和后续的回调方法可以共享信息, 会在后续的调用中, 传递给handler.
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 异步操作accept成功调用；
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    // 读取client的额数据, 不过这里的read()方法也是异步的。返回的结果是一个Future。
                    // 这里直接调用Future.get()方法进行等待，将此异步方法变成了同步的方法。所以当此句执行
                    // 完毕，数据读取也就完成了。
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    // 数据回写给client。write()方法同样是异步的，同样返回的是Future对象。
                    writeResult = result.write(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }  finally {
                    try {
                        // 进行下一个连接的准备。同时关闭当前正在处理的客户端连接，但是在关闭之前，还是需要先确保write()操作已经完成
                        serverSocketChannel.accept(null, this);
                        // 所以这里需要调用Future.get()方法进行等待。
                        writeResult.get();
                        // 关闭client连接。
                        result.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 异步操作accept失败调用；
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("Failed:" + exc);
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 调用start开启服务器，但是由于start里面的方法都是异步的，所以它会立即返回，所以并不会像
        // 阻塞方法那样等待
        new AIOServer().start();
        // 所以，如果要让程序驻守执行，那么循环语句是必须的。否则上面的调用返回主线程就直接退出了。
        while (true) {
            Thread.sleep(100);
        }
    }
}
