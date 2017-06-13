package book2.ch14;

/**
 * Author by darcy
 * Date on 17-6-13 上午10:22.
 * Description:
 */
public class BoundedBuffer<T> extends BaseBoundedBuffer<T> {
    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    // 条件谓词: not-empty
    // 条件谓词: not-full

    /**
     * 使用条件队列上的方法(wait, notify, notifyAll)之前需要首先获得锁.
     * 只有能对状态进行检测,那么才能在条件上进行等待. 而只有能够修改状态的时候,才能从条件等待中释放锁.
     * @param t
     * @throws InterruptedException
     */
    public synchronized void put(T t) throws InterruptedException {
        while (isFull()) {
            // 自动释放锁,并且挂起当前线程.
            wait();
        }
        doInput(t);
        //通知事件发生．
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T element = doExtract();
        notifyAll();
        return element;
    }
}
