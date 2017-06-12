package book2.ch11;

/**
 * Author by darcy
 * Date on 17-6-12 上午9:55.
 * Description:
 * 同步策略: buckets[i] 由buckets[i / N_LOCKS]来保护.
 */
public class StrippedMap {
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    public StrippedMap(int bucketNumber) {
        this.buckets = new Node[bucketNumber];
        this.locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    private static class Node {
        Object key;
        Object value;
        Node next;
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node node = buckets[hash]; node != null; node = node.next) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }


}
