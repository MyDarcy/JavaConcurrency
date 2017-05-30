package book1.ch7;

import akka.actor.UntypedActor;

/**
 * Author by darcy
 * Date on 17-5-28 下午10:16.
 * Description:
 */
public class Greeter extends UntypedActor {

    // 定义了两种消息类型.
    static enum Msg {
        GREET, DONE;
    }

    /**
     * Actor启动之前, 本方法会被Akka框架回调，初始化处理。
     * @param o
     * @throws Throwable
     */
    @Override
    public void onReceive(Object o) throws Throwable {
        if (o == Msg.GREET) {
            System.out.println("hello, world.");
            // 向消息发送方发送DONE消息。
            getSender().tell(Msg.DONE, getSelf());
        } else {
            unhandled(o);
        }
    }
}
