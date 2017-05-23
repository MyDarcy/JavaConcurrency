package main.java.book1.ch4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author by darcy
 * Date on 17-5-23 下午8:19.
 * Description:
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> money = new AtomicReference<>();

    public static void main(String[] args) {
        money.set(19);
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        while (true) {
                            Integer m = money.get();
                            if (m < 20) {
                                if (money.compareAndSet(m, m + 20)) {
                                    System.out.println("余额小于20, 充值成功, 新的余额:" + money.get());
                                    break;
                                }
                            } else {
                                // money > 20
                                break;
                            }
                        }
                    }
                }
            }.start();
        }

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    while (true) {
                        Integer m = money.get();
                        if (m > 10) {
                            System.out.println("大于10");
                            if (money.compareAndSet(m, m - 10)) {
                                System.out.println("成功消费10元, 余额:" + money.get());
                                break;
                            }
                        } else {
                            System.out.println("没有足够的余额");
                            break;
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
