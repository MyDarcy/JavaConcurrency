package book1.ch5.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-26 下午5:17.
 * Description:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        PCDataFactory factory = new PCDataFactory();
        // 设置环形缓冲区的大小为1024(2^10),
        int bufferSize = 1024;
        // 创建disruptor对象, 封装了整个disruptor的调用;
        Disruptor<PCData> disruptor = new Disruptor<PCData>(factory,
                bufferSize,
                es,
                ProducerType.MULTI,
                new BlockingWaitStrategy());

        // 设置了数据的消费者，这里设置4个消费者实例，系统会为每一个消费者实例映射到
        // 一个线程, 所以这里提供了4个消费者线程.
        disruptor.handleEventsWithWorkerPool(
                new Consumer(),
                new Consumer(),
                new Consumer(),
                new Consumer()
        );

        // 启动并且初始化disruptor对象,
        disruptor.start();
        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        // 由生产者向缓冲区中存放数据.
        for (long l = 0; true; ++l) {
            byteBuffer.putLong(0, l);
            System.out.println("add data " + l);
            producer.pushData(byteBuffer);
            Thread.sleep(100);

        }
    }

}
