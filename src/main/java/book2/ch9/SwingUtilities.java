package book2.ch9;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-6-11 下午4:42.
 * Description:
 *
 * 使用ExecutorService来实现SwingUtilities...
 */
public class SwingUtilities {
    private static final ExecutorService exec
            = Executors.newSingleThreadExecutor(new SwingThreadFactory());

    private static volatile Thread swingThread;

    public static class SwingThreadFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            swingThread = new Thread(r);
            return swingThread;
        }
    }

    public static boolean isEventDispatchThread() {
        return Thread.currentThread() == swingThread;
    }

    public static void invokeLater(Runnable task) {
        exec.execute(task);
    }

    public static void invokdeAndWait(Runnable task) throws InterruptedException, InvocationTargetException {
        Future f = exec.submit(task);
        try {
            f.get();
        }  catch (ExecutionException e) {
            throw new InvocationTargetException(e);
        }
    }

}
