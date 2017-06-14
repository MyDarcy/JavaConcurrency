package book2.ch15;

/**
 * Author by darcy
 * Date on 17-6-14 上午11:22.
 * Description:
 */
public class CasCounter {
    private SimulatedCAS cas;

    public CasCounter(SimulatedCAS cas) {
        this.cas = cas;
    }

    public int get() {
        return cas.get();
    }

    public int increment() {
        int oldValue;
        do {
            oldValue = cas.get();
            // 进行比较和交换的时候旧值已经不是oldValue了，那么while返回true，继续循环. 如果cas操作时
            // 旧值就是oldValue，根据lock能保证cas替换成功，并且返回的是旧值．退出循环，返回oldValue + 1;
        } while (oldValue != cas.compareAndSwap(oldValue, oldValue + 1));
        return oldValue + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        CasCounter casCounter = new CasCounter(new SimulatedCAS());
        new Thread(new Task(casCounter)).start();
        new Thread(new Task(casCounter)).start();
        Thread.sleep(1000);
        System.out.println(casCounter.get());
    }
}

class Task implements Runnable {

    CasCounter counter;

    public Task(CasCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
}
