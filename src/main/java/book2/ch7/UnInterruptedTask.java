package book2.ch7;

import java.util.concurrent.BlockingQueue;

/**
 * Author by darcy
 * Date on 17-6-9 下午6:25.
 * Description:
 * 不可中断的任务但是可调用可中断的阻塞方法:
 *     在无限循环中调用可中断的阻塞方法，发现失败之后重试　- retrying...。
 * 但是需要在本地保存中断状态.
 */
public class UnInterruptedTask {

    /**
     *
     * @param queue
     * @return
     */
    public Integer getNextInteger(BlockingQueue<Integer> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    // 入口处检查中断状态，那么一旦发现中断状态已经设置了，立即抛出InterruptedException异常。
                    // 如此就可能导致死循环。
                    /*if (!Thread.currentThread().isInterrupted()) {
                        return queue.take();
                    }*/
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    // 恢复工作。
                }
            }
        } finally {
            // finally语句会先于return语句之前执行。　所以是在返回之前恢复状态。
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
