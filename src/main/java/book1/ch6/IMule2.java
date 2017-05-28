package book1.ch6;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:05.
 * Description:
 */
public class IMule2 implements IAnimal, IHorse, IDonkey {

    @Override
    public void eat() {
        System.out.println("Mule eat.");
    }

    @Override
    public void run() {
        IHorse.super.run();
    }

    public static void main(String[] args) {
        IMule mule = new IMule();
        mule.run();
        mule.breath();
    }
}
