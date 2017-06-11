package book2.ch10;

/**
 * Author by darcy
 * Date on 17-6-11 下午5:39.
 * Description:
 */
public class AvoidDeadLockWithLockOrder {
    private static final Object whenEqual = new Object();

    /**
     * 以hash值先小后大的方式加锁.
     * 通过identityHashCode来定义加锁顺序.
     * @param from
     * @param to
     * @param amount
     */
    public static void transferMoney(Account from, Account to, int amount) {
        class Helper {
            public void transfer() {
                if (from.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("money is not enough");
                } else {
                    from.decBalance(amount);
                    to.incBalance(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (whenEqual) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }


}
