package book2.ch14;

/**
 * Author by darcy
 * Date on 17-6-13 上午9:23.
 * Description:
 */
public class BaseBoundedBuffer<T> {
    private final T[] items;
    private int tail;
    private int head;
    private int count;

    public BaseBoundedBuffer(int capacity) {
        this.items = (T[]) new Object[capacity];
    }

    protected synchronized void doInput(T t) {
        items[tail] = t;
        tail = (++tail == items.length) ? 0 : tail;
        count++;
    }

    protected synchronized T doExtract() {
        T element = items[head];
        //
        items[head] = null;
        head = (++head == items.length) ? 0 : head;
        --count;
        return element;
    }

    public synchronized final boolean isFull() {
        return count == items.length;
    }

    public synchronized final boolean isEmpty() {
        return 0 == count;
    }

}
