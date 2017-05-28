package book1.ch6;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:00.
 * Description:
 */
public interface IDonkey {
    void eat();
    default void run() {
        System.out.println("IDonkey run...");
    }
}
