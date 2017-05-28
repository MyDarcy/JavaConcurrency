package book1.ch6;

import java.util.Arrays;
import java.util.Random;

/**
 * Author by darcy
 * Date on 17-5-28 下午4:30.
 * Description:
 */
public class ParallelDemo {

    private static long test1(int[] array) {
        Random random = new Random();
        // 串行化生成
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Arrays.setAll(array, (index) -> random.nextInt());
        }
        return (System.currentTimeMillis() - start) / 5;
        /*System.out.println("Time:" + (System.currentTimeMillis() - start) + "ms");*/
    }

    private static long test2(int[] array) {
        Random random = new Random();
        // 串行化生成
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Arrays.parallelSetAll(array, (index) -> random.nextInt());
        }
        return (System.currentTimeMillis() - start) / 5;
    }

    public static void main(String[] args) {
        int[] array = new int[10000000];

        long time = test1(array);

        long time2 = test2(array);
        System.out.println("Time1:" + time + "ms");
        System.out.println("Time2:" + time2 + "ms");
    }
}
