package book2.ch7;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author by darcy
 * Date on 17-6-10 下午9:13.
 * Description:
 *
 * 方法中需要一次执行一批任务，并且所有的任务都处理完毕的时候才返回．
 */
public class OneTimeTaskDemo {
    boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        // 声明为final防止在Runnable中被修改.
        final AtomicBoolean hasNewMail = new AtomicBoolean();
        try {
            for (String host : hosts) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    }
                });
            }
        } finally {
            es.shutdown();
            es.awaitTermination(timeout, unit);
        }
        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        return false;
    }
}
