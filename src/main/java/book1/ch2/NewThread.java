package main.java.book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-18 下午2:49.
 * Description:
 */
public class NewThread {
    public static void main(String[] args) {
        Thread t1 = new Thread();
        // Thread有一个run方法, start()方法就会新建一个线程并且让这个线程执行run()方法;
        t1.start();

        // 此时并没有创建新的线程，而是在当前线程中调用run()方法, 只是作为一个普通方法被调用；
        t1.run();
    }
}
