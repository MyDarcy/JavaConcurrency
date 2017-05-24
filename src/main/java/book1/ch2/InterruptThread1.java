package book1.ch2;

/**
 * Author by darcy
 * Date on 17-5-18 下午10:08.
 * Description:
 */
public class InterruptThread1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true) {
                    Thread.yield();
                }
            }
        };

        t1.start();
        Thread.sleep(2000);
        // 这里虽然对t1进行了中断，但是在t1中并没有进行中断处理的逻辑,
        // 因此, 即使t1线程被设置了中断状态, 但是这个中断不会发生任何作用
        t1.interrupt();

        Thread t2 = new Thread(){
            @Override
            public void run() {
                while (true) {
                    // 判断当前线程是否被中断了, 如果是, 退出循环体, 结束线程;
                    // 和前面的stopme标记来终止线程类似,只是这里是原生支持的，更为强劲;
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted.");
                        break;
                    }
                    Thread.yield();
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted.");
                        break;
                    }
                    try {
                        // 如果此时线程被中断, 程序就会抛出异常, 并转入异常处理,
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted while sleep.");

                        // 已经捕获了中断，可以立即退出线程，但是为了后续的处理, 保证数据的完整性和一致性,
                        // 因此执行了interrupt()方法再次中断自己, 置中断标志位, 如此，上面的条件检查线
                        // 程中断中，才会发现当前线程已经被中断了;
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        t3.start();
        Thread.sleep(2000);
        t3.interrupt();
    }
}
