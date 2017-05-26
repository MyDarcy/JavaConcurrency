package book1.ch5.pc;

/**
 * Author by darcy
 * Date on 17-5-26 下午3:38.
 * Description:
 */
public class PCData {
    private final int data;

    public PCData(int data) {
        this.data = data;
    }

    public PCData(String data) {
        this.data = Integer.valueOf(data);
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "data:" + data;
    }
}
