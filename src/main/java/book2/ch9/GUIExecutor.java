package book2.ch9;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-6-11 下午4:48.
 * Description:
 */
public class GUIExecutor extends AbstractExecutorService {

    private static final GUIExecutor instance = new GUIExecutor();

    private GUIExecutor() {}

    public static GUIExecutor getInstance() {
        return instance;
    }

    @Override
    public void shutdown() {
        instance.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return instance.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return instance.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return instance.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return instance.awaitTermination(timeout, unit);
    }
    @Override
    public void execute(Runnable command) {
        if (SwingUtilities.isEventDispatchThread()) {
            command.run();
        } else {
            SwingUtilities.invokeLater(command);
        }
    }
}
