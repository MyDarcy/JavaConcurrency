package book1.ch3;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Author by darcy
 * Date on 17-5-21 下午10:06.
 * Description:
 */
public class ConcurrencySkipListMapDemo {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap();
        for (int i = 0; i < 20; i++) {
            map.put(i, i);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println();
        for (Integer key : map.keySet()) {
            System.out.println(key + "\t" + map.get(key));
        }
    }
}
