package book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-19 下午2:23.
 * Description:
 */
public class ThreadGroupName implements Runnable {

    @Override
    public void run() {
        String groupName = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        while (true) {
            System.out.println("I am " + groupName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("PrintThreadGroup");
        // t1, t2加入线程组
        Thread t1 = new Thread(tg, new ThreadGroupName(), "T1");
        Thread t2 = new Thread(tg, new ThreadGroupName(), "T2");
        t1.start();
        t2.start();
        // activeCount()只是估计值而不是精确值;
        System.out.println(tg.activeCount());
        // 列出线程组中的所有信息;
        tg.list();
    }
}
