package book3.chapter05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 10-19
 */
public class ProcessData {
    private static final ReentrantReadWriteLock rwl       = new ReentrantReadWriteLock();
    private static final Lock                   readLock  = rwl.readLock();
    private static final Lock                   writeLock = rwl.writeLock();
    private volatile boolean                    update    = false;

    public void processData() {
        readLock.lock();
        if (!update) {

            readLock.unlock();

            writeLock.lock();
            try {
                if (!update) {

                    update = true;
                }
                readLock.lock();
            } finally {
                writeLock.unlock();
            }

        }
        try {

        } finally {
            readLock.unlock();
        }
    }

}
