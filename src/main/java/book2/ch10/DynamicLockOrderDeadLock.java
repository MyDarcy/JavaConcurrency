package book2.ch10;

/**
 * Author by darcy
 * Date on 17-6-11 下午5:33.
 * Description:
 */
public class DynamicLockOrderDeadLock {
    /**
     * 虽然不同的线程调用本方法都是先from后to,但是这种顺序完全是运行时指定的,就是说存在两个线程以相反的顺序来
     * 加锁可能性.
     * @param from
     * @param to
     * @param amount
     */
    public void transferMoney(Account from, Account to, int amount) {
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("money not enough...");
                } else {
                    from.decBalance(amount);
                    to.incBalance(amount);
                }
            }
        }
    }
}
