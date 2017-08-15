package book3.chapter04;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;


public class MultiThread {

    public static void main(String[] args) {
        /**
         * 获取线程管理MXBean
         */
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        /**
         *
         * 不需要获取同步的monitor和synchronizer信息，仅仅获取线程和堆栈信息
         */
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(
            false, false);

        /**
         * 遍历线程，打印线程ID和名称信息
         */
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId()
                + "] " + threadInfo.getThreadName());
        }
    }
}
/**
 * [5] Monitor Ctrl-Break --
 * [4] Signal Dispatcher -- 分发处理发送给JVM信号的线程
 * [3] Finalizer-- 调用对象的finalizer方法的线程
 * [2] Reference Handler -- 清除reference线程
 * [1] main -- main线程, 用户入口程序
 */
