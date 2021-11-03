package Self_Testing.Concurrency.CompletableFutureTesting;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class CountDownLatchTest {
    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CountDownLatch latch = new CountDownLatch(2);

        AtomicInteger a = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();

        Supplier<String> sleep3 = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.set(10);
            latch.countDown();
            return "Had been sleep for 3s";
        };

        Supplier<String> sleep5 = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.set(20);
            latch.countDown();
            return "Had been sleep for 5s";
        };

        CompletableFuture<String> slept2 = CompletableFuture.supplyAsync(sleep3, threadPool);
        CompletableFuture<String> slept3 = CompletableFuture.supplyAsync(sleep5, threadPool);

        System.out.println("Main is here");

        try {
            // 当前线程阻塞, 直到countDownLatch为0, 或者是到时间了
            latch.await(3, TimeUnit.SECONDS);
            // 阻塞被唤醒
            System.out.println(a.get());
            System.out.println(b.get());
            System.out.println("Main runs into this");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
