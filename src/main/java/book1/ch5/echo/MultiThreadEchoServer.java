package book1.ch5.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-27 上午11:18.
 * Description:
 */
public class MultiThreadEchoServer {
    // 使用线程池来处理client的连接。
    private static ExecutorService es = Executors.newCachedThreadPool();

    /**
     * 读取client发送过来的内容, 然后原样发回.
     */
    static class HandleMsg implements Runnable {
        private Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            BufferedReader input = null;
            PrintWriter output = null;

            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);

                // 从client获取内容
                String inputLine = null;
                long start = System.currentTimeMillis();
                while ((inputLine = input.readLine()) != null) {
                    output.println(inputLine);
                }
                long end = System.currentTimeMillis();
                // 处理一次client请求所花费的时间, 包括读取和回写的时间.
                System.out.println("spend:" + (end - start) + "ms");
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
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            // 8000端口等待,
            serverSocket = new ServerSocket(8000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                // 一旦右新的client连接到来, 那么就新开一个线程进行处理
                clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connected.");
                es.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
