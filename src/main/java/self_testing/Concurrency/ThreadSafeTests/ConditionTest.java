package self_testing.Concurrency.ThreadSafeTests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    static Lock lock = new ReentrantLock();

    Condition theCondition = lock.newCondition();

    public void consumer() {
        lock.lock();
        try {
            System.out.println("C: Get the Lock, wait the item\n");
            // 阻塞该线程, 对Condition进行await
            theCondition.await();
            System.out.println("C: Consumer the item\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void produce() {
        lock.lock();
        try {
            System.out.println("P: Get the Lock, producing the item\n");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("P: Finished production\n");
            // 唤醒该Condition
            theCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ConditionTest test = new ConditionTest();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        threadPool.execute(test::consumer);
        threadPool.execute(test::consumer);
        threadPool.execute(test::produce);
        threadPool.execute(test::produce);
    }


}
