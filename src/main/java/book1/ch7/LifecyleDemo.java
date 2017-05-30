package book1.ch7;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-5-29 下午10:13.
 * Description:
 */
public class LifecyleDemo {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lifecycle", ConfigFactory.load("lifecycle.conf"));
        customerStrategy(system);
    }

    private static void customerStrategy(ActorSystem system) {
        ActorRef supervisor = system.actorOf(Props.create(Supervisor.class), "lifecycle");
        // 向supervisor发送Props对象。
        supervisor.tell(Props.create(RestartActor.class), ActorRef.noSender());

        ActorSelection actorSelection = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
        for (int i = 0; i < 100; i++) {
            actorSelection.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
        }



    }
}
