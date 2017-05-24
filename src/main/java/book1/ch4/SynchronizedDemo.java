package book1.ch4;

/**
 * Author by darcy
 * Date on 17-5-22 下午9:35.
 * Description:
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        SynchronizedDemo test02 = new SynchronizedDemo();
        //启动预热
        for (int i = 0; i < 10000; i++) {
            i++;
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            test02.append("abc", "def");
        }
        System.out.println("Time=" + (System.currentTimeMillis() - start));
    }

    public void append(String str1, String str2) {
        StringBuffer sb = new StringBuffer();
        sb.append(str1).append(str2);
    }
}
