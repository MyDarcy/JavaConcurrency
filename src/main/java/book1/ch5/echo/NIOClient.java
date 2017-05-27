package book1.ch5.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Author by darcy
 * Date on 17-5-27 下午9:00.
 * Description:
 */
public class NIOClient {
    private Selector selector;

    public void init(String ip, int port) throws IOException {
        // 创建SocketChannel实例, 并设置为非阻塞模式;
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = SelectorProvider.provider().openSelector();
        // 非阻塞的， 所以connect方法返回的时候, 连接不一定成功。后续使用这个连接时，还需要
        // 通过finishConnect()再次进行确认。
        socketChannel.connect(new InetSocketAddress(ip, port));
        // 将Channel和Selector进行绑定，同时注册对连接事件(OP_CONNECT)感兴趣。
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            // 没有任何事件准备好, 这里就会阻塞。主要处理的事件
            // 是表示连接就绪的Connect事件和表示通道可读的Read事件。
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()) {
                    connect(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 连接是否已经建立, 建立连接后, 向Channel写入数据，并注册读事件为感兴趣的事件。
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        socketChannel.write(ByteBuffer.wrap(new String("Hello, Server\n").getBytes()));
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Channel可读时执行read方法.
     * @param key
     * @throws IOException
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        socketChannel.read(byteBuffer);
        byte[] bytes = byteBuffer.array();
        String msg = new String(bytes).trim();
        System.out.println("client receive msg:" + msg);
        // 最后关闭Channel和Selector。
        socketChannel.close();
        key.selector().close();
    }

    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient();
        client.init("localhost", 8000);
        client.working();
    }
}
