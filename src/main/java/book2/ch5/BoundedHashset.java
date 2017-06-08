package book2.ch5;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Author by darcy
 * Date on 17-6-8 上午10:32.
 * Description:
 *
 * 通过semaphore实现有界阻塞容器set,
 */
public class BoundedHashset<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashset(int semaphoreNumber) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.semaphore = new Semaphore(semaphoreNumber);
    }

    public boolean add(T t) throws InterruptedException {
        // 获取许可。
        semaphore.acquire();
        boolean result = false;
        try {
            result = set.add(t);
            return result;
        } finally {
            // 添加元素失败，释放semaphore...
            if (!result) {
                semaphore.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean result = set.remove(o);
        if (result) {
            semaphore.release();
        }
        return result;
    }
}
