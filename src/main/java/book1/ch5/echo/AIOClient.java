package book1.ch5.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Author by darcy
 * Date on 17-5-27 下午9:59.
 * Description:
 */
public class AIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        // client连接指定的服务器, 并注册了一系列事件。这个过程完全是异步的。
        client.connect(new InetSocketAddress("localhost", 8000), null,
                new CompletionHandler<Void, Object>() {

                    // 连接成功的回调函数
                    @Override
                    public void completed(Void result, Object attachment) {
                        client.write(ByteBuffer.wrap("hello!".getBytes()), null,
                                new CompletionHandler<Integer, Object>() {
                            // 向服务器端发送数据成功的回调函数。
                            @Override
                            public void completed(Integer result, Object attachment) {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                    // 从服务器端读取回写数据, 成功读取所有数据后, 回调函数。
                                    @Override
                                    public void completed(Integer result, ByteBuffer attachment) {
                                        buffer.flip();
                                        System.out.println(new String(buffer.array()));
                                        try {
                                            client.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {

                                    }
                                });
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {

                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {

                    }
                });
        Thread.sleep(100);
    }
}
