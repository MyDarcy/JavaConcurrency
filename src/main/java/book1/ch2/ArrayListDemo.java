package main.java.book1.ch2;

import java.util.ArrayList;

/**
 * Author by darcy
 * Date on 17-5-19 下午3:43.
 * Description:
 */
public class ArrayListDemo {
    static ArrayList<Integer> list = new ArrayList<>();

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new AddThread();
        Thread t2 = new AddThread();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(list.size());
    }
}
