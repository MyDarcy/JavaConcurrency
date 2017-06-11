package book2.ch8;

import java.util.concurrent.ThreadFactory;

/**
 * Author by darcy
 * Date on 17-6-11 下午12:10.
 * Description:
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }
    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }
}
