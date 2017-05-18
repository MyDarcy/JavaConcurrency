package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-18 下午9:07.
 * Description:
 */

class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    User() {
        this.id = 0;
        this.name = "0";
    }

    @Override
    public String toString() {
        return "User [" + id + ", name=" + name + "]";
    }
}

public class StopThreadUnsafe {
    public static User user = new User();

    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    user.setId(v);
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName("" + v);
                }
                Thread.yield();
            }
        }
    }

    // 自行决定线程何时退出...
    public static class ChangeObjectThread2 extends Thread {

        // 定义了一个stopme变量, 指示线程是否需要退出
        volatile boolean stopme = false;

        public void stopMe() {
            this.stopme = true;
        }

        @Override
        public void run() {
            while (true) {

                // 当检测到stopme被设置时, 线程就会退出了?
                if (stopme) {
                    System.out.println("exit by stop me");
                    break;
                }

                synchronized (user) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    user.setId(v);
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName("" + v);
                }
                Thread.yield();
            }
        }
    }



    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    if (user.getId() != Integer.parseInt(user.getName())) {
                        System.out.println(user);
                    }
                }
                // 就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，让自己或者其它的线程运行。
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true) {
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }


}
