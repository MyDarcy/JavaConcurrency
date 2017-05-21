package main.java.book1.ch3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author by darcy
 * Date on 17-5-21 下午7:43.
 * Description:
 */
public class ConconrrentHashMapDemo {
    public final static int THREAD_POOL_SIZE = 5;
    public static Map<String, Integer> hashTable = null;
    public static Map<String, Integer> synchronizedHashMap = null;
    public static Map<String, Integer> concurrentHashMap = null;
    public static void main(String[] args) throws InterruptedException {

        hashTable = new Hashtable<>();
        test(hashTable);

        synchronizedHashMap = Collections.synchronizedMap(new HashMap<String, Integer>());
        test(synchronizedHashMap);

        concurrentHashMap = new ConcurrentHashMap<>();
        test(concurrentHashMap);
    }
    public static void test(final Map<String, Integer> map) throws InterruptedException {
        System.out.println("Test started for: " + map.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                executorService.execute(new Runnable() {
                    @SuppressWarnings("unused")
                    @Override
                    public void run() {
                        for (int i = 0; i < 500000; i++) {
                            Integer i1 = (int) Math.ceil(Math.random() * 550000);
                            // Retrieve value. We are not using it anywhere
                            Integer i2 = map.get(String.valueOf(i1));
                            // Put value
                            map.put(String.valueOf(i1), i1);
                        }
                    }
                });
            }
            // Make sure executor stops
            executorService.shutdown();
            // Blocks until all tasks have completed execution after a shutdown request
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("2500K entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + map.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }
}
