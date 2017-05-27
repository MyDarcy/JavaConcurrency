package book1.ch5.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.zip.InflaterOutputStream;

/**
 * Author by darcy
 * Date on 17-5-27 下午1:39.
 * Description:
 */
public class HeavySocketClient {
    private static ExecutorService es = Executors.newCachedThreadPool();
    private static final int SLEEP_TIME = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {
        @Override
        public void run() {
            Socket client = null;
            PrintWriter output = null;
            BufferedReader input = null;

            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                output = new PrintWriter(client.getOutputStream(), true);
                output.print("H");
                LockSupport.parkNanos(SLEEP_TIME);
                output.print("e");
                LockSupport.parkNanos(SLEEP_TIME);
                output.print("l");
                LockSupport.parkNanos(SLEEP_TIME);
                output.print("l");
                LockSupport.parkNanos(SLEEP_TIME);
                output.print("o");
                LockSupport.parkNanos(SLEEP_TIME);
                output.print("!");
                LockSupport.parkNanos(SLEEP_TIME);
                output.println();
                output.flush();

                input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("data from server:" + input.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            EchoClient echoClient = new EchoClient();
            // 发起10次请求,每个请求中, client会慢慢进行向服务器传送数据, 整个过程会持续6s;
            for (int i = 0; i < 10; i++) {
                es.execute(echoClient);
            }
        }
    }

}
