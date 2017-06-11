package book2.ch10;

/**
 * Author by darcy
 * Date on 17-6-11 下午5:34.
 * Description:
 */
public class Account {
    Integer balance = new Integer(0);

    public Account(int i) {
        balance = i;
    }

    public Integer getBalance() {
        return balance;
    }

    public void decBalance(int amount) {
        balance -= amount;
    }

    public void incBalance(int amount) {
        balance += amount;
    }
}
