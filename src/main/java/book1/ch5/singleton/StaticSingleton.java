package book1.ch5.singleton;

/**
 * Author by darcy
 * Date on 17-5-26 下午3:11.
 * Description:
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingelton init");
    }

    private static class StaticSingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return StaticSingletonHolder.instance;
    }
}
