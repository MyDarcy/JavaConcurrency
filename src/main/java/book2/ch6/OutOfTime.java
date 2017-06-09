package book2.ch6;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Author by darcy
 * Date on 17-6-9 上午10:06.
 * Description:
 *
 * 推荐使用ScheduledThreadPoolExecutor.
 */
public class OutOfTime {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {

        public void run() {
            throw new RuntimeException();
        }
    }


}
