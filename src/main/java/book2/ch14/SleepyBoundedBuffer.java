package book2.ch14;

import java.util.concurrent.ThreadFactory;

/**
 * Author by darcy
 * Date on 17-6-13 上午9:54.
 * Description:
 */
public class SleepyBoundedBuffer<T> extends BaseBoundedBuffer<T> {

    public SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(T element) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doInput(element);
                    return;
                }
            }
            Thread.sleep(1000);
        }
    }

    /**
     * 问题就在于持有锁的同时去休眠,那么其他线程就无法在不持有锁的情况下修改Buffer的状态\
     * ,所以可能一致就这样等待下去.
     * @return
     * @throws InterruptedException
     */
    public T take() throws InterruptedException {
        T item;
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doExtract();
                }
            }
            Thread.sleep(1000);

        }
    }
}
