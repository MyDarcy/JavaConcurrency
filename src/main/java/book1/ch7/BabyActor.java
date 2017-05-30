package book1.ch7;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

/**
 * Author by darcy
 * Date on 17-5-30 下午1:17.
 * Description:
 */
public class BabyActor extends UntypedActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public static enum Msg {
        SLEEP, PLAY, CLOSE,
    }

    Procedure<Object> angry = new Procedure<Object>() {
        @Override
        public void apply(Object msg) throws Exception {
            System.out.println("angryApply:" + msg);
            if (msg == Msg.SLEEP) {
                getSender().tell("I am already angry.", getSelf());
                System.out.println("I am already angry.");
            } else if (msg == Msg.PLAY) {
                System.out.println("I like Playing");
                getContext().become(happy);
            }
        }
    };

    Procedure<Object> happy = new Procedure<Object>() {
        @Override
        public void apply(Object msg) throws Exception {
            System.out.println("happyApply:" + msg);
            if (msg == Msg.PLAY) {
                getSender().tell("I am already happy", getSelf());
                System.out.println("I am already happy");
            } else if (msg == Msg.SLEEP) {
                System.out.println("I dont wanna to sleep.");
                getContext().become(angry);
            }
        }
    };

    /**
     * 当onReceive()方法处理SLEEP消息时, 会切换当前的Actor状态为angry,
     * 如果处理PLAY消息, 会切换当前Ator状态为happy。但是一旦完成状态取切换,
     * 后续有新的消息到达, 那么后续的消息就交由当前状态处理。
     * @param msg
     * @throws Throwable
     */
    @Override
    public void onReceive(Object msg) throws Throwable {
        System.out.println("onReceive:" + msg);
        if (msg == Msg.SLEEP) {
            getContext().become(angry);
        } else if (msg == Msg.PLAY) {
            getContext().become(happy);
        } else {
            unhandled(msg);
        }
    }
}
