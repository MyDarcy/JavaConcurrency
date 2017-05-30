package book1.ch7.inbox;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Author by darcy
 * Date on 17-5-29 下午1:42.
 * Description:
 */
public class MyWorker extends UntypedActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    static enum Msg {
        WORKONG, DONE, CLOSE;
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg == Msg.WORKONG) {
            System.out.println("I am working");
        }
        if (Msg.DONE == msg) {
            System.out.println("stop working");
        }
        if (Msg.CLOSE == msg) {
            System.out.println("I will shutdown.");
            // 给发送者发送Done消息。
            getSender().tell(Msg.CLOSE, getSelf());
            // Actor停止。
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }
}
