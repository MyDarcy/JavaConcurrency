package book3.chapter03;

import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockExample {
    int           a    = 0;
    ReentrantLock lock = new ReentrantLock();

    public void writer() {
        lock.lock(); //
        try {
            a++;
        } finally {
            lock.unlock(); //
        }
    }

    public void reader() {
        lock.lock(); //
        try {
            int i = a;
            //
        } finally {
            lock.unlock(); //
        }
    }
}
