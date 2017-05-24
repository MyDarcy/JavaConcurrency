package book1.ch4.philosopher;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-24 上午10:49.
 * Description:
 */
class Chopstick {
    private int index;
    private boolean use = false;

    public Chopstick(int index) {
        super();
        this.index = index;
    }

    @Override
    public String toString() {
        return "Chopstick [index=" + index + "]";
    }

    /*
     * 获得筷子
     * 该筷子被获得之后，当有其他哲学家线程来请求获得时，都需要等待
     */
    public synchronized void take() throws InterruptedException{
        while(use)
            wait();
        use =true;
    }

    /*
     * 归还筷子
     * 当持有该筷子的哲学家使用完毕之后，就将其归还，并通知其他在等待该筷子资源的哲学家
     */
    public synchronized void drop(){
        use = false;
        notifyAll();
    }
}

class Philosopher implements Runnable{
    private Chopstick right ;
    private Chopstick left;
    private int index;
    private int thinkTime;
    public Philosopher(Chopstick right, Chopstick left, int index, int thinkingTime) {
        super();
        this.right = right;
        this.left = left;
        this.index = index;
        this.thinkTime = thinkingTime;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " thinking .......");
                thinking();
                System.out.println(this+" start to eat and take right stick");
                right.take();
                System.out.println(this+" take left stick");
                left.take();
                System.out.println(this+" eating");
                thinking();//吃饭
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this+"InterruptedException");
        }

    }

    /**
     * 哲学家思考时间，由thinkingTime因子决定
     * @throws InterruptedException
     */
    private void thinking() throws InterruptedException{
        Thread.sleep(thinkTime*100);
    }

    @Override
    public String toString() {
        return "Philosopher [index=" + index + "]";
    }
}

public class FixPholosopherProblem {

    /**
     * 会产生死锁的版本
     * 5个哲学家，5只筷子，每个哲学家吃饭之前需要先拿到右边的筷子，然后再拿到左边的筷子
     * 之后才能吃饭
     * @throws InterruptedException
     */
    @Test
    public void deadLock() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        int size=5;
        int thinkingTime=0;
        Chopstick[] chopstick = new Chopstick[size];
        for(int i=0;i<size;i++)
            chopstick[i] = new Chopstick(i);
        for(int i=0;i<size;i++)
            executor.execute(new Philosopher(chopstick[i], chopstick[(i+1)%size], i, thinkingTime));
        Thread.sleep(4*1000);
        executor.shutdownNow();
    }

    /**
     * 破坏产生死锁的循环条件
     * 使第五个哲学家不按照先获得右边筷子，再获得左边筷子的方式进行
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        int size=5;
        int thinkingTime = 0;
        Chopstick[] chopsticks = new Chopstick[size];
        for(int i=0;i<size;i++)
            chopsticks[i]=new Chopstick(i);
        for(int i=0;i<size-1;i++)
            executor.execute(new Philosopher(chopsticks[i], chopsticks[i+1], i, thinkingTime));
        executor.execute(new Philosopher(chopsticks[0], chopsticks[size-1], size, thinkingTime));//更改第五个哲学家获得筷子的顺序
        Thread.sleep(100*1000);
        executor.shutdownNow();
    }
}
