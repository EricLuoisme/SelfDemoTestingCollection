package Self_Testing.Concurrency.LockTesting;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class ReentrantLockTest {

    public static void main(String[] args) {

        SharedStuff sharedStuff = new SharedStuff();

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Supplier<String> add = () -> {
            boolean b = sharedStuff.reentrantLock.tryLock();
            try {
                // 获取锁
                if (b) {
                    System.out.println("add获取到锁");
                    Integer counts = sharedStuff.getCounts();
                    counts++;
                    sharedStuff.setCounts(counts);
                    TimeUnit.SECONDS.sleep(3);
                } else {
                    System.out.println("add获取不到锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                sharedStuff.reentrantLock.unlock();
                System.out.println("add释放锁");
            }
            return "add finished";
        };

        Supplier<String> minus = () -> {
            // 获取锁
            boolean b = sharedStuff.reentrantLock.tryLock();
            try {
                if (b) {
                    System.out.println("minus获取到锁");
                    Integer counts = sharedStuff.getCounts();
                    counts++;
                    sharedStuff.setCounts(counts);
                    TimeUnit.SECONDS.sleep(3);
                } else {
                    System.out.println("minus获取不到锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                sharedStuff.reentrantLock.unlock();
                System.out.println("minus释放锁");

            }
            return "minus finished";
        };

        CompletableFuture.supplyAsync(add, threadPool).whenComplete((s, t) -> System.out.println(s));
        CompletableFuture.supplyAsync(add, threadPool).whenComplete((s, t) -> System.out.println(s));
        CompletableFuture.supplyAsync(minus, threadPool).whenComplete((s, t) -> System.out.println(s));

        System.out.println("main run out");

        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("main finally stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class SharedStuff {

        ReentrantLock reentrantLock;

        Integer counts;

        public SharedStuff() {
            this.reentrantLock = new ReentrantLock();
            this.counts = 0;
        }

        public void setCounts(Integer counts) {
            this.counts = counts;
        }

        public Integer getCounts() {
            return counts;
        }
    }
}
