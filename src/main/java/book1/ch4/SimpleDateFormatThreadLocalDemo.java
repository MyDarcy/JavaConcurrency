package main.java.book1.ch4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-5-23 下午3:12.
 * Description:
 */
public class SimpleDateFormatThreadLocalDemo {

    // SimpleDateFormat并不是线程安全的。
    public static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (sdf.get() == null) {
                    sdf.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date date = sdf.get().parse("2017-05-22 15:21:" + (i % 60));
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
    }
}
