package book2.ch6;

import java.util.concurrent.Executor;

/**
 * Author by darcy
 * Date on 17-6-9 上午9:15.
 * Description:
 *
 * 单线程中运行每一个任务
 * 就是在原来的线程中运行原任务。
 */
public class WithinThreadExecutor implements Executor{
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
