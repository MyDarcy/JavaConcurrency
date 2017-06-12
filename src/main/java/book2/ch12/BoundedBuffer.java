package book2.ch12;

import java.util.concurrent.Semaphore;

/**
 * Author by darcy
 * Date on 17-6-12 上午11:15.
 * Description:
 *
 * 使用信号量来实现有界缓冲.
 */
public class BoundedBuffer<E> {
    private final Semaphore availableItems;
    private final Semaphore availableSpaces;
    private E[] items;
    private int putPosision, takePosition;

    public BoundedBuffer(int capacity) {
        this.availableItems = new Semaphore(0);
        this.availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        // 可用空间为0,就是满的.
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E elemnt) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(elemnt);
        availableItems.release();
    }

    private synchronized void doInsert(E elemnt) {
        int i = putPosision;
        items[i] = elemnt;
        putPosision = (++i == items.length) ? 0 : i;
    }

    public E take() throws InterruptedException {
        // 阻塞直到获取一个许可.
        availableItems.acquire();
        E item = doTake();
        // 返回一个许可给信号量.
        availableSpaces.release();
        return item;
    }

    private synchronized E doTake() {
        int i = takePosition;
        E item = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return item;
    }


}
