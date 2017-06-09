package book2.ch7;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Author by darcy
 * Date on 17-6-9 下午10:23.
 * Description:
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream inputStream;

    public ReaderThread(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    /**
     * 既能处理中断,也能处理关闭底层的套接字.
     */
    @Override
    public void interrupt() {
        try {
            // 关闭底层的套接字,抛出异常
            socket.close();
        } catch (IOException e) {

        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] bytes = new byte[1024];
            while (true) {
                int count = inputStream.read(bytes);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    System.out.println(new String(bytes, 0, count));
                }
            }
        } catch (IOException e) {
            // 允许线程退出.
        }
    }
}
