package book1.ch7;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Author by darcy
 * Date on 17-5-29 下午1:26.
 * Description:
 */
public class HelloWorldDemo {
    public static void main(String[] args) {
        // 管理和维护Actor的ActorSystem。"Hello"为系统名，
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));
        // 创建顶级的Acotr， HelloWorld。
        ActorRef actorRef = system.actorOf(Props.create(HelloWorld.class));
        System.out.println("HelloWorld Actor Path:" + actorRef.path());
    }
}
