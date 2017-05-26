package book1.ch5.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:49.
 * Description:
 */
public class MyFutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造FutureTask, 其中的Callable封装了实际数据的构造过程
        FutureTask<String> futureTask = new FutureTask<>(new MyRealData("python"));
        ExecutorService es = Executors.newFixedThreadPool(1);

        // 执行了FutureTask, 相当于上例子中client.request(str)发送请求，
        // 这里会开启线程进行RealData的call()执行.
        es.submit(futureTask);

        System.out.println("Request end.");
        try {
            // 这里用sleep代替了对其他业务逻辑的处理。
            // 在主线程处理业务逻辑的时候, 线程池开启了一个线程进行真实数据的构造.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 相当于上例子中的data.getResult(), 取得call()方法的返回值.
        // 如果call方法没有执行完成，那么这里依旧会阻塞。
        System.out.println("Data=" + futureTask.get());
    }
}
