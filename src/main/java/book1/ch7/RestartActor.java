package book1.ch7;

import akka.actor.UntypedActor;
import scala.Option;

/**
 * Author by darcy
 * Date on 17-5-29 下午10:05.
 * Description:
 */
public class RestartActor extends UntypedActor {
    public enum Msg {
        DONE, RESTART;
    }

    // 如下都是生命周期的回调接口.
    @Override
    public void preStart() throws Exception {
        System.out.println("preStart. hashCode:" + hashCode());
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("postStop. hashCode:" + hashCode());
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        System.out.println("postRestart. hashCode:" + hashCode());
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        System.out.println("preRestart. hashCode:" + hashCode());
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o == Msg.DONE) {
            System.out.println(getSelf().path());
            getContext().stop(getSelf());
        } else if (o == Msg.RESTART) {
            System.out.println(getSelf().path());
            System.out.println(((Object) null).toString());
            int i = 0 / 0;
        }
        unhandled(o);
    }
}
