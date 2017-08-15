package book3.chapter04;


public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner());
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Daemon线程的finally块在线程退出时不一定会执行.
     * 所以构建Daemon线程时,不能依靠finally中的内容来确保执行关闭或者清理资源.
     */
    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(100);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
