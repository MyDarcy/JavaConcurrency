package book1.ch5.singleton;

/**
 * Author by darcy
 * Date on 17-5-26 下午2:43.
 * Description:
 */
public class Singleton {
    private Singleton() {
        System.out.println("Singleton is create");
    }

    private static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }
}
