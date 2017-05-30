package book1.ch7.inbox;

import akka.actor.*;
import book1.ch7.*;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Author by darcy
 * Date on 17-5-29 下午10:36.
 * Description:
 */
public class InBoxDemo {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(book1.ch7.MyWorker.class), "worker");

        // 根据system创建与之绑定的Inbox;
        Inbox inbox = Inbox.create(system);
        // 用邮箱监视worker; 这样就能够在worker停止以后得到一个通知。
        inbox.watch(worker);
        // 通过邮箱向worker发送消息。
        inbox.send(worker, MyWorker.Msg.WORKONG);
        inbox.send(worker, MyWorker.Msg.DONE);
        inbox.send(worker, MyWorker.Msg.CLOSE);

        // 接收消息。
        while (true) {
            Object msg = inbox.receive(Duration.create(2, TimeUnit.SECONDS));
            if (msg == MyWorker.Msg.CLOSE) {
                System.out.println("Inbox my worker is closing");
                // worker停止工作了。
            } else if (msg instanceof Terminated) {
                System.out.println("Inbox my worker is dead");
                Future<Terminated> fu = system.terminate();
                Await.result(fu, Duration.create("1 seconds"));
                break;
            } else {
                System.out.println(msg);
            }
        }
    }
}
