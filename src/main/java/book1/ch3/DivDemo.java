package book1.ch3;

import java.util.concurrent.*;

/**
 * Author by darcy
 * Date on 17-5-21 下午3:18.
 * Description:
 */
public class DivDemo {
    static class DivTask implements Runnable {
        int a;int b;

        public DivTask(int a, int b) {
            this.a = a; this.b = b;
        }

        @Override
        public void run() {
            double result = a / b;
            System.out.println(result);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            Future future = tpe.submit(new DivTask(100, i));
            future.get();
        }
    }
}
