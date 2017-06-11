package book2.ch10;

import java.util.Random;

/**
 * Author by darcy
 * Date on 17-6-11 下午7:19.
 * Description:
 */
public class DeadLockDemo {
    public static final int THREAD_NUMBER = 20;
    public static final int ACCOUNT_NUMBER = 5;
    public static final int ITERATIONS = 1000000;
    final AvoidDeadLockWithLockOrder instance = new AvoidDeadLockWithLockOrder();

    public static void main(String[] args) {
        final Random random = new Random(31);
        final Account[] accounts = new Account[ACCOUNT_NUMBER];
        for (int i = 0; i < ACCOUNT_NUMBER; i++) {
            accounts[i] = new Account(1000000000);
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int j = 0; j < ITERATIONS; j++) {
                    int from = random.nextInt(ACCOUNT_NUMBER);
                    int to = random.nextInt(ACCOUNT_NUMBER);
                    int amount = random.nextInt(100);
                    AvoidDeadLockWithLockOrder.transferMoney(accounts[from], accounts[to], amount);
                }
            }
        }

        for (int i = 0; i < THREAD_NUMBER; i++) {
            new TransferThread().start();
        }

    }


}
