package book1.ch5.disruptor;

/**
 * Author by darcy
 * Date on 17-5-26 下午4:54.
 * Description:
 */
public class PCData {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}
