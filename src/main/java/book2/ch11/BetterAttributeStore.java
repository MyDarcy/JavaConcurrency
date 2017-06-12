package book2.ch11;

import java.util.HashSet;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-12 上午9:39.
 * Description:
 *
 * 使用多个锁来保护互不相关的状态变量, 减少锁请求的频率.
 */
public class BetterAttributeStore {
    private final Set<String> users =  new HashSet<>();
    private final Set<String> queries = new HashSet<>();

    public void addUser(String user) {
        synchronized (users) {
            users.add(user);
        }
    }

    public void removeUser(String user) {
        synchronized (users) {
            users.remove(user);
        }
    }

    public void addQuery(String query) {
        synchronized (queries) {
            queries.add(query);
        }
    }

    public void removeQuery(String query) {
        synchronized (queries) {
            queries.remove(query);
        }
    }
}
