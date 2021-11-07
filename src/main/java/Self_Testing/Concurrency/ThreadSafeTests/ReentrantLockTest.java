package Self_Testing.Concurrency.ThreadSafeTests;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    static Lock lock = new ReentrantLock();

    private static int count = 0;

    public static void inc() {
        lock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 没用锁的情况, 就可以发现输出<5000的
        for (int i = 0; i < 5000; i++) {
            new Thread(() -> ReentrantLockTest.inc()).start();
        }
        TimeUnit.SECONDS.sleep(7);
        System.out.println(count);
    }

}
