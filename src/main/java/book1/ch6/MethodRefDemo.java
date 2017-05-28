package book1.ch6;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-5-28 下午3:48.
 * Description:
 */
public class MethodRefDemo {

    /**
     * 函数式接口
     * @param <U>
     */
    @FunctionalInterface
    interface UserFactory<U extends User> {
        U create(int id, String name);
    }

    public static void main(String[] args) {
        // 当使用User::new构造接口实例时,
        // 系统会根据UserFactory.create()的函数签名来选择合适的User构造函数。
        UserFactory<User> userFactory = User::new;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // create的调用都会委托给User的实际构造函数进行。
            users.add(userFactory.create(i, "user" + i));
        }
        users.stream().map(User::getName).forEach(System.out::println);
    }
}
