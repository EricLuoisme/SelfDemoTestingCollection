package Self_Testing.Concurrency.CompletableFutureTesting;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableExecutorTest {

    public static void main(String[] args) {

        // 仅设置2个线程, 强制其实现复用
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Supplier<String> sleep5Supplier = () -> {
            System.out.println("异步睡5s线程 -> 开始: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步睡5s线程 -> 结束: " + Thread.currentThread().getName());
            return "sleep 5s";
        };

        Supplier<String> sleep17Supplier = () -> {
            System.out.println("异步睡17s线程 -> 开始: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步睡17s线程 -> 结束: " + Thread.currentThread().getName());
            return "sleep 17s";
        };

        CompletableFuture<String> completableFuture17 = CompletableFuture.supplyAsync(sleep17Supplier, executorService);
        CompletableFuture<String> completableFuture51 = CompletableFuture.supplyAsync(sleep5Supplier, executorService);

        completableFuture51.whenComplete((s, throwable) -> {
            System.out.println("异步任务结束回调 -> " + s);
            System.out.println("回调线程为: " + Thread.currentThread().getName());
        }).thenApplyAsync(s -> {
            System.out.println("Apply第二个");
            return "yeah";
        });

        completableFuture17.whenComplete((s, throwable) -> {
            System.out.println("异步任务结束回调 -> " + s);
            System.out.println("回调线程为: " + Thread.currentThread().getName());
        });

        // 从打印结果可用看出, 虽然CompletableFuture.supplyAsync已经将任务分发出去, 但是主线程没有等待(区别于Future的最大优化)
        // 更大程度的利用, 每一个时刻
        System.out.println("main 结束");
        try {
            TimeUnit.SECONDS.sleep(20);
            System.out.println("main 睡完");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
