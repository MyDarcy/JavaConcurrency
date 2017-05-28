package book1.ch6;

import java.util.concurrent.locks.StampedLock;

/**
 * Author by darcy
 * Date on 17-5-28 下午8:12.
 * Description:
 */

public class Point {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    void move(double deltaX, double deltaY) { // an exclusively locked method
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            // 释放写锁。
            sl.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() { // A read-only method
        // 尝试一次乐观读; 返回邮戳整数, 作为锁获取的凭证。
        long stamp = sl.tryOptimisticRead();
        // 获取x, y值。
        double currentX = x, currentY = y;
        // 判断stamp在读取x, y的过程中是否被修改。
        // 一旦修改，可以在死循环中使用乐观读，直到成功为止.
        // 这里使用的是锁升级的策略, 将乐观锁升级为悲观锁.
        if (!sl.validate(stamp)) {
            // 获得悲观读锁, 如果当前对象正在被修改, 那么读锁的申请可能会导致线程的挂起.
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                // 释放读锁。
                sl.unlockRead(stamp);
            }
        }
        // 没有被修改,
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
