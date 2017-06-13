package book2.ch14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author by darcy
 * Date on 17-6-13 下午7:18.
 * Description:
 *
 * 基于Condition来实现有界缓冲．
 * 将条件谓词分成两个等待集合．更容易分析和实现．
 * 满足三元关系: 锁, 条件谓词, 条件变量.条件谓词中的变量必须使用锁来保护，在检查条件谓词
 * 和使用await和signal等时，必须先持有锁．
 */
public class ConditionBoundedBuffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final T[] items;
    private int rear, head, count;

    public ConditionBoundedBuffer(int itemsNumber) {
        this.items = (T[]) new Object[itemsNumber];
    }

    public void put(T element) {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[rear] = element;
            if (++rear == items.length) {
                count = 0;
            }
            count++;
            notEmpty.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }
            T element = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.notifyAll();
            return element;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
