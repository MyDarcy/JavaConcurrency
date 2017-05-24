package book1.ch3;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author by darcy
 * Date on 17-5-21 下午6:27.
 * Description:
 */
public class SynchronizedHashMapDemo {
    public static void main(String[] args) {
        Map map = Collections.synchronizedMap(new HashMap<>());
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
    }
}
