package book1.ch6;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:59.
 * Description:
 */
public class ArraysStreamDemo {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // 当前的流是IntStream, 所以Consumer是IntConsumer;
        Arrays.stream(array).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });

        // 显示的声明参数是final的;
        Arrays.stream(array).forEach((final int x) -> {
            System.out.println(x);
        });

        // 参数的类型是可以推导的。
        Arrays.stream(array).forEach((x) -> {
            System.out.println(x);
        });

        Arrays.stream(array).forEach((x) -> System.out.println(x));

        Arrays.stream(array).forEach(System.out::println);

        IntConsumer outPrintln = System.out::println;
        IntConsumer errPrintln = System.err::println;
        Arrays.stream(array).forEach(outPrintln.andThen(errPrintln));
    }
}
