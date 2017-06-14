package book2.ch15;

/**
 * Author by darcy
 * Date on 17-6-14 上午11:09.
 * Description:
 * 锁的方式来实现CAS语义.
 */
public class SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    /**
     * 返回旧值．
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int exceptedValue, int newValue) {
        // 参照上面，　expectedValue等于返回的旧值，那么就是CAS成功了，否则就是没有替换成功．
        return exceptedValue == compareAndSwap(exceptedValue, newValue);
    }

}
