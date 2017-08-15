package book3.chapter04;

/**
 * 同步块的实现使用了monitorenter和monitorexit指令，
 * 而同步方法则依靠方法修饰符ACC_SYNCHRONIZED来完成, 无论采用
 * 那一种方式，本质上都是对一个对象监视器monitor进行获取，同时这个获取过程是排它的
 * 也就是同一时刻只有一个线程获取到由synchronized所保护对象的监视器.
 */
public class Synchronized {
    public static void main(String[] args) {

        synchronized (Synchronized.class) {

        }

        m();
    }

    public static synchronized void m() {
    }
}

/*

 */