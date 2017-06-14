package book2.ch15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author by darcy
 * Date on 17-6-14 下午12:54.
 * Description:
 */
public class CasNumberRange {
    private static class Pair{
        final int lower;
        final int upper;

        public Pair() {
            lower = 0;
            upper = 0;
        }

        public Pair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<Pair> values
            = new AtomicReference<>(new Pair(0, 0));
    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            Pair oldPair = values.get();
            if (i > oldPair.upper) {
                throw new IllegalArgumentException("提供的下界值比上届值大");
            }
            Pair newPair = new Pair(i, oldPair.upper);
            // 设置成功.
            if (values.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }

    public void setUpper(int i) {
        while (true) {
            Pair oldPair = values.get();
            if (i < oldPair.lower) {
                throw new IllegalArgumentException("upper-value provided is lower than old pair lower value");
            }
            Pair newPair = new Pair(oldPair.lower, i);
            if (values.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }
}
