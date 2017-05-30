package book1.ch7;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Author by darcy
 * Date on 17-5-29 下午1:55.
 * Description:
 */
public class WatchActor extends UntypedActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public WatchActor(ActorRef actorRef) {
        getContext().watch(actorRef);
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg instanceof Terminated) {
            System.out.println(String.format("%s terminated, shutting down system", ((Terminated) msg).getActor().path()));
            Future<Terminated> future = context().system().terminate();
            // shutdown deprecated, now this may useful...
            Await.result(future, Duration.create("10 seconds"));
        } else {
            unhandled(msg);
        }
    }
}
