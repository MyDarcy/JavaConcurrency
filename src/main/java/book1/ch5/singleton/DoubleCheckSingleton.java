package book1.ch5.singleton;

/**
 * Author by darcy
 * Date on 17-5-26 下午2:59.
 * Description:
 */
public class DoubleCheckSingleton {
    private static DoubleCheckSingleton instance = null;


    public DoubleCheckSingleton() {
        // TODO Auto-generated constructor stub
        System.out.println("DoubleCheckSingleton init.");
    }


    public static DoubleCheckSingleton getInstance(){
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
