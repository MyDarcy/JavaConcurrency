package book1.ch5.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Author by darcy
 * Date on 17-5-27 下午12:38.
 * Description:
 */
public class EchoClient {
    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter output = null;
        BufferedReader input = null;
        try {
            socket = new Socket();
            // 连接服务器
            socket.connect(new InetSocketAddress("127.0.0.1", 8000));
            output = new PrintWriter(socket.getOutputStream(), true);
            // 向服务器发送内容。
            output.println("Hello, server");
            output.flush();

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 读取服务器发送来的内容.
            System.out.println("From Server:" + input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
