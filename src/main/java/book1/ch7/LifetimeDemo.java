package book1.ch7;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Author by darcy
 * Date on 17-5-29 下午2:02.
 * Description:
 */
public class LifetimeDemo {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("LifetimeDemo", ConfigFactory.load("samplehello.conf"));
        // 创建worker和watcher。
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
        // create()方法第一个参数为要创建的Actor类型，第二个参数为Actor构造函数的参数,这里是worker。
        system.actorOf(Props.create(WatchActor.class, worker), "watcher");
        // 向worker发送两条消息。
        worker.tell(MyWorker.Msg.WORKONG, ActorRef.noSender());
        worker.tell(MyWorker.Msg.DONE, ActorRef.noSender());
        // 向workder发送特殊的消息PoisionPill。 PoisionPill就是毒药丸，直接毒死对方，让其终止。
        worker.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
