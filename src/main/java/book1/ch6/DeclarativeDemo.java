package book1.ch6;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Author by darcy
 * Date on 17-5-28 下午2:25.
 * Description:
 */
public class DeclarativeDemo {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 11, 13, 15};
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }

        Arrays.stream(array).forEach(System.out::print);

        Arrays.stream(array).map((x) -> x * x).forEach(System.out::println);
        Arrays.stream(array).forEach(System.out::println);

        Comparator<String> comparator = Comparator.comparingInt(String::length)
                .thenComparing(String.CASE_INSENSITIVE_ORDER);
    }
}

@FunctionalInterface
interface IntHandler{
    void handle();

    boolean equals(Object o);
}
