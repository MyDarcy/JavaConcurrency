package book1.ch5.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Author by darcy
 * Date on 17-5-26 下午5:01.
 * Description:
 */
public class Producer {
    // 环形缓冲区
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将产生的数据放入到缓冲区中：将传入的ByteBuffer中的数据提取出来, 并装载到环形缓冲区中。
     * @param data ByteBuffer可以包装任何类型的数据, 这里包装long类型的数据。
     */
    public void pushData(ByteBuffer data) {
        // 得到下一个可用的序列号.
        long sequence = ringBuffer.next();
        try {
            // 通过序列号，取得下一个空闲的PCData对象,
            PCData pcData = ringBuffer.get(sequence);
            // 将PCData对象设置为期望值.
            pcData.set(data.getLong(0));
        } finally {
            // 只有发布后的数据才会真正的被消费者看见.
            ringBuffer.publish(sequence);
        }
    }
}
