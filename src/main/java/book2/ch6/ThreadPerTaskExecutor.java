package book2.ch6;

import java.util.concurrent.Executor;

/**
 * Author by darcy
 * Date on 17-6-9 上午9:14.
 * Description:
 *
 * 为每个任务新开一个线程。
 */
public class ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
