package book2.ch14;

/**
 * Author by darcy
 * Date on 17-6-13 上午9:34.
 * Description:
 */
public class GrumpyBoundedBuffer<T> extends BaseBoundedBuffer<T> {
    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(T t) throws BufferedFullException {
        if (isFull()) {
            throw new BufferedFullException("Full Buffer Exception.");
        }
        doInput(t);
    }

    public synchronized T take() throws BufferedEmptyException {
        if (isEmpty()) {
            throw new BufferedEmptyException("Empty Buffer Exception.");
        }
        return doExtract();
    }
}
