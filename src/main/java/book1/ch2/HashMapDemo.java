package book1.ch2;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by darcy
 * Date on 17-5-19 下午3:49.
 * Description:
 */
public class HashMapDemo {
    static Map<String, String> maps = new HashMap<>();

    public static class AddThread implements Runnable {
        int start;

        AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i += 2) {
                maps.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(maps.size());
    }

}
