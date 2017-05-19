package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 下午2:32.
 * Description:
 */
public class DaemonDemo {
    public static class DemoT extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("Alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new DemoT();
        // 先设置为daemon后启动线程而不是反过来，否则守护线程设置失败，被当作用户线程执行;
        thread.setDaemon(true);
        thread.start();

        // main是用户线程，thread是守护线程。
        Thread.sleep(2000);
    }
}
