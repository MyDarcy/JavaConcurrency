package book1.ch6;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:17.
 * Description:
 */
public class LambdaDemo {
    public static void main(String[] args) {
        // lambda表达式内部访问的外部变量num必须被声明为final的，这样才能在lambda表达式中合法的访问它。
        final int num = 10;
        Function<Integer, Integer> convertor = (from) -> from * num;
        System.out.println(convertor.apply(3));

        int number = 100;
        Function<Integer, Integer> convertor2 = (from) -> from * number;
        System.out.println(convertor2.apply(3));

        // 编译错误, Variable used in lambda expression should be final or effective fianl.
        /*int numberFactor = 1000;
        Function<Integer, Integer> convertor3 = (x) -> x * numberFactor;
        numberFactor++;
        System.out.println(convertor3.apply(3));*/


    }
}
