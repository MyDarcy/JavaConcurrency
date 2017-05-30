package book1.ch7;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-5-29 下午10:00.
 * Description:
 */
public class Supervisor extends UntypedActor {
    // Actor遇到错误后, 一分钟内进行3次重试, 超过这个频率, 直接杀死Actor。
    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create(10, TimeUnit.SECONDS),
            new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
                    if (t instanceof ArithmeticException) {
                        System.out.println("meet ArithmeticException, just resume");
                        // 不做任何处理。
                        return SupervisorStrategy.resume();
                    } else if (t instanceof NullPointerException) {
                        System.out.println("meet NullPointerException, restart");
                        return SupervisorStrategy.restart();
                    } else if (t instanceof IllegalArgumentException) {
                        return SupervisorStrategy.stop();
                    } else {
                        return SupervisorStrategy.escalate();
                    }
                }
            });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Props) {
            // 根据props对象生成一个restartActor;
            getContext().actorOf((Props) o, "restartActor");
        } else {
            unhandled(o);
        }
    }
}
