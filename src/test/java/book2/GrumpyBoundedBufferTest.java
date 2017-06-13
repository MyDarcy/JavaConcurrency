package book2;

import book2.ch14.BufferedEmptyException;
import book2.ch14.GrumpyBoundedBuffer;
import junit.framework.TestCase;
import scala.Int;

/**
 * Author by darcy
 * Date on 17-6-13 上午9:41.
 * Description:
 * 将失败条件传递给调用者.
 */
public class GrumpyBoundedBufferTest extends TestCase {
    private GrumpyBoundedBuffer<Integer> buffer
            = new GrumpyBoundedBuffer<>(10);

    /**
     * 问题就在于一旦阻塞,必须等待一段时间再去获取值,这里并不会释放锁.导致低响应性.
     * @throws InterruptedException
     */
    public void testTake() throws InterruptedException {
        while (true) {
            try {
                Integer item = buffer.take();
            } catch (BufferedEmptyException e) {
                Thread.sleep(1000);
            }
        }
    }
}
