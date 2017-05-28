package book1.ch6;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:01.
 * Description:
 */
public interface IAnimal {
    default void breath() {
        System.out.println("Animal breath...");
    }
}
