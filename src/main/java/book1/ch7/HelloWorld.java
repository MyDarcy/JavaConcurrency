package book1.ch7;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

/**
 * Author by darcy
 * Date on 17-5-28 下午10:22.
 * Description:
 */
public class HelloWorld extends UntypedAbstractActor {
    ActorRef greeter;

    /**
     * Actor启动之前, 会被Akka框架调用, 完成一些初始化工作.
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        // 创建Greeter实例，是HelloWorld的子Actor(使用的是HelloWorld的上下文)。
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        System.out.println("Greeter Actor Path:" + greeter.path());
        // 向Greeter实例发送GREET消息。
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    /**
     * HelloWorld的消息处理函数
     * @param o
     * @throws Throwable
     */
    @Override
    public void onReceive(Object o) throws Throwable {
        // 处理DONE消息, 接收到Done消息时，再向greeter发送GREET消息，然后终止自己。
        if (o == Greeter.Msg.DONE) {
            // 再次发送GREET信息。getSelf()代表消息发送方。
            greeter.tell(Greeter.Msg.GREET, getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(o);
        }
    }
}
