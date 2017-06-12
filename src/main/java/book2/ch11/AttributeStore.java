package book2.ch11;

import java.util.HashSet;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-12 上午9:39.
 * Description:
 *
 * 互不相关的状态使用同一个锁...
 */
public class AttributeStore {
    private final Set<String> users =  new HashSet<>();
    private final Set<String> queries = new HashSet<>();

    public synchronized void addUser(String user) {
        users.add(user);
    }

    public synchronized void removeUser(String user) {
        users.remove(user);
    }

    public synchronized void addQuery(String query) {
        queries.add(query);
    }

    public synchronized void removeQuery(String query) {
        queries.remove(query);
    }
}
