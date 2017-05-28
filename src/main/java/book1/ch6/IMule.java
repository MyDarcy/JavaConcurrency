package book1.ch6;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:03.
 * Description:
 */
public class IMule implements IHorse, IAnimal {

    @Override
    public void eat() {
        System.out.println("Imule eat...");
    }

    public static void main(String[] args) {
        IMule mule = new IMule();
        mule.run();
        mule.breath();
    }
}
