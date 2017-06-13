package book2.ch14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Author by darcy
 * Date on 17-6-13 下午8:29.
 * Description:
 *
 * 使用AQS实现的二元闭锁.
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    /**
     * 打开闭锁.
     */
    public void signal() {
        sync.releaseShared(0);
    }

    /**
     * 阻塞, 直到打开闭锁．
     * @throws InterruptedException
     */
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    public

    class Sync extends AbstractQueuedSynchronizer {
        /**
         *
         * @param arg
         * @return　１非独占的方式获取锁. -1获取操作失败.
         */
        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            // 打开闭锁
            setState(1);
            // 其他线程可以获取闭锁．
            return true;
        }
    }

}
