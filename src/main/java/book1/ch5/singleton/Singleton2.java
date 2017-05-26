package book1.ch5.singleton;

/**
 * Author by darcy
 * Date on 17-5-26 下午2:55.
 * Description:
 */
public class Singleton2 {
    private Singleton2() {
        System.out.println("Singletion Lazy init.");
    }

    private static Singleton2 instance;

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            return new Singleton2();
        }
        return instance;
    }
}
