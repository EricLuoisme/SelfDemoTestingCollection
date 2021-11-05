package Self_Testing.Concurrency.ThreadSafeTests;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    /**
     * 有三种情况可以避免变量修改, 线程间不可见问题
     * <p>
     * 1. 使用volatile发现是可以的
     * 2. 对t1线程使用Thread.sleep(0), 发现t1也会结束 (因为sleep(0), 也会触发CPU切换, 所以值会同步)
     * 3. 对t1线程使用sout打印内容, 发现t1也会结束 (因为IO操作, 也会触发synchronized操作, 所以值也会同步)
     */

    public static volatile boolean stop = false;
//    public static boolean stop = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
            System.out.println("t1 stop");
        });
        t1.start();

        System.out.println("Started the thread");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 会发现, Main线程对stop值的修改, 对t1线程不可见, 导致无法结束线程t1
        // 这也是因为, 线程在执行时, 会首先从堆中获取所需变量内容, 将其copy一份到自己的线程变量区
        // 所以, Main线程的和t1线程的是两份拷贝, 没有办法share修改
        stop = true;
    }

}
