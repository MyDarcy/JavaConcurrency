package book1.ch7;

import akka.actor.UntypedActor;

/**
 * Author by darcy
 * Date on 17-5-28 下午10:16.
 * Description:
 */
public class Greeter extends UntypedActor {

    static enum Msg {
        GREET, DONE;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o == Msg.GREET) {
            System.out.println("hello, world.");
            getSender().tell(Msg.DONE, getSelf());
        } else {
            unhandled(o);
        }
    }
}
