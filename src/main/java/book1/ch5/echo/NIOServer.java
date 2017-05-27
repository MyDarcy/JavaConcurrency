package book1.ch5.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-27 下午3:12.
 * Description:
 */
public class NIOServer {
    private Selector selector;
    private ExecutorService es = Executors.newCachedThreadPool();
    // 统计某一个Socket上花费的时间.
    public static Map<Socket, Long> socketTimes = new HashMap<>(10240);
    private void startServer() throws IOException {
        // 工厂方法获得一个Selector对象.
        selector = SelectorProvider.provider().openSelector();
        // 获得表示服务器段的ServerSocketChannel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置ServerSocketChannel为非阻塞模式;
        ssc.configureBlocking(false);

//        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        InetSocketAddress isa = new InetSocketAddress(8000);
        // 端口绑定
        ssc.bind(isa);
        // 将ServerSocketChannel绑定到selector上, 同时注册它感兴趣的事件为OP_ACCEPT;
        // SelectionKey表示一对Selector和Channel的关系, 当Channel注册到Selector上时, 就相当于确定了
        // 两者之间的服务关系，SelectionKey就是这个契约. 当Selecotr或者Channel被关闭的时候, 他们对应的SeletionKey就会失效
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 处理消息.
        for(; ;) {
            // select()方法是一个阻塞方法, 如果当前没有任何数据准备好，就会等待，一旦有数据可读，就会返回.
            // 返回值是已经准备就绪的SelectionKey的数量
            selector.select();
            // 因为selector为多个Chnanel服务，因此已经准备就绪的Channel就可能有多个. 所以后面就是比遍历集合，逐个处理。
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iter = readyKeys.iterator();
            long e = 0;
            while (iter.hasNext()) {
                SelectionKey sk = (SelectionKey) iter.next();
                // 元素移除, 否则就会重复处理相同的SelectionKey。
                iter.remove();

                // Acceptable状态，处理client端的连接
                if (sk.isAcceptable()) {
                    doAccept(sk);
                    // 可读，处理数据的读取.
                } else if (sk.isValid() && sk.isReadable()) {
                    if (!socketTimes.containsKey(((SocketChannel) sk.channel()).socket())) {
                        socketTimes.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                    }
                    doRead(sk);
                    // 可写，处理数据的发送，同时输出这个socket连接的耗时。
                } else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = socketTimes.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("Spend:" + (e - b) + "ms");
                }
            }
        }
    }

    /**
     * 与client建立连接.
     * @param sk
     */
    private void doAccept(SelectionKey sk) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            // 返回的clientChannel表示和client端通信的通道.
            clientChannel = serverSocketChannel.accept();
            // 非阻塞, 即要求系统在准备好IO后, 再通知线程读取或者写入.
            clientChannel.configureBlocking(false);

            // 生成的clientChannel注册到selector选择器上, 同时向selector注册其感兴趣的事件。
            // 这样当Selector发现这个Channel已经准备好读的时候, 就能够给线程一个通知。
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            NIOEchoClient client = new NIOEchoClient();
            // 将client实例附加到表示这个连接的SelectionKey上，这样整个连接的处理过程中就可以共享这个client实例。
            clientKey.attach(client);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accept connection from:" + clientAddress.getHostAddress() + ".");
        } catch (IOException e) {
            System.out.println("Fail to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        // 获取对应的Client Channel,通过此Channel Server和Client通信。
        SocketChannel socketChannel = (SocketChannel) sk.channel();
        // 准备8Kb的缓冲区读取数据。
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
        int length = 0;
        try {
            length = socketChannel.read(byteBuffer);
            if (length < 0) {
                disconnect(sk);
                return;
            }
        } catch (IOException e) {
            System.out.println("Fail to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        // 读取完成，重置缓冲区，为数据处理做准备。
        byteBuffer.flip();
        // 使用单独的线程进行数据的处理，而不用阻塞任务派发线程。
        es.execute(new HandleMsg(sk, byteBuffer));
    }

    /**
     * 简单的将数据放到client端的队列中,同时进行必要的业务逻辑处理.
     */
    class HandleMsg implements Runnable {
        private SelectionKey sk;
        ByteBuffer byteBuffer;

        public HandleMsg(SelectionKey sk, ByteBuffer byteBuffer) {
            this.sk = sk;
            this.byteBuffer = byteBuffer;
        }
        @Override
        public void run() {
            NIOEchoClient client = (NIOEchoClient) sk.attachment();
            client.enQueue(byteBuffer);
            // 数据处理完毕以后, 就可以将结果回写到client端, 因此需要重新注册感兴趣的消息事件，
            // 将写操作也作为感兴趣的事件进行提交。这样Channel准备好写入的时候, 就能够通知线程。
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            // 强迫selector立即返回。
            selector.wakeup();
        }
    }


    private void doWrite(SelectionKey sk) {
        SocketChannel socketChannel = (SocketChannel) sk.channel();
        // 通过SelectionKey,可以在doRead和doWrite中共享同一个client实例。
        NIOEchoClient client = (NIOEchoClient) sk.attachment();
        LinkedList<ByteBuffer> toClientQueue = client.getToClientQueue();

        // 获得列表顶部元素, 准备回写。
        ByteBuffer byteBuffer = toClientQueue.getLast();
        try {
            int length = socketChannel.write(byteBuffer);
            if (length == -1) {
                disconnect(sk);
                return;
            }

            // 全部发送完成, 移除缓存对象.
            if (byteBuffer.remaining() == 0) {
                toClientQueue.removeLast();
            }
        } catch (IOException e) {
            System.out.println("Fail to write to client");
            e.printStackTrace();
            disconnect(sk);
        }

        // 全部数据发送完成, 需要将写事件从感兴趣的操作中移除.
        // 如果不这么做, 那么每次Channel准备好写的时候, 都会来执行doWrite()方法, 而实际上, 又无数据可写。
        if (toClientQueue.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();

        InetAddress clientAddress = channel.socket().getInetAddress();
        System.out.println(clientAddress.getHostAddress() + " disconnected.");

        try {
            channel.close();
        } catch (Exception e) {
            System.out.println("Failed to close client socket channel.");
            e.printStackTrace();
        }
    }

    // Main entry point.
    public static void main(String[] args) {
        NIOServer echoServer = new NIOServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            System.out.println("Exception caught, program exiting...");
            e.printStackTrace();
        }
    }

}

/**
 * 封装了一个队列, 保存需要回复给这个client的所有消息.
 */
class NIOEchoClient {
    private LinkedList<ByteBuffer> toClientQueue;

    public NIOEchoClient() {
        toClientQueue = new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getToClientQueue() {
        return toClientQueue;
    }

    public void enQueue(ByteBuffer byteBuffer) {
        toClientQueue.addFirst(byteBuffer);
    }
}