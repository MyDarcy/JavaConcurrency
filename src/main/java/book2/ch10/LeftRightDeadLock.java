package book2.ch10;

/**
 * Author by darcy
 * Date on 17-6-11 下午5:25.
 * Description:
 *
 * 不同的顺序来请求相同的锁.
 */
public class LeftRightDeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void  rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomething();
            }
        }
    }

    private void doSomething() {

    }


}
