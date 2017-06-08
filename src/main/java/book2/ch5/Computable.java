package book2.ch5;

/**
 * Author by darcy
 * Date on 17-6-8 下午9:00.
 * Description:
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
