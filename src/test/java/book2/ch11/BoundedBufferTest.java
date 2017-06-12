package book2.ch11;

import book2.ch12.BoundedBuffer;
import junit.framework.TestCase;

/**
 * Author by darcy
 * Date on 17-6-12 下午12:42.
 * Description:
 */
public class BoundedBufferTest extends TestCase {

    /**
     * 测试创建时候是否为空.
     */
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);
        assertTrue(boundedBuffer.isEmpty());
        assertFalse(boundedBuffer.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            boundedBuffer.put(i);
        }
        assertFalse(boundedBuffer.isEmpty());
        assertTrue(boundedBuffer.isFull());
    }

    private static final int LOCK_TIME = 10;
    public void testTakeBlockWhenEmpty(){
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);
        Thread take = new Thread(){
            @Override
            public void run() {
                try {
                    int ele = boundedBuffer.take();
                    System.out.println("should not here. for blocked.");
                } catch (InterruptedException e) {

                }
            }
        };
        try {
            take.start();
            Thread.sleep(LOCK_TIME * 1000);
            take.interrupt();
            // 如果take线程可中断,那么join方法会很快返回...
            take.join(LOCK_TIME * 1000);
            assertFalse(take.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
