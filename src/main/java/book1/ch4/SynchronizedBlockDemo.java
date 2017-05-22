package main.java.book1.ch4;

/**
 * Author by darcy
 * Date on 17-5-22 下午8:32.
 * Description:
 */
public class SynchronizedBlockDemo {
    public void mothod() {
        synchronized (this) {
            System.out.println("Method 1 Start.");
        }
    }
}
