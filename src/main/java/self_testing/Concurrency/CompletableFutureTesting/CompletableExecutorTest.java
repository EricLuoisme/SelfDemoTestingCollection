package self_testing.Concurrency.CompletableFutureTesting;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompletableExecutorTest {

    public static void main(String[] args) {

        // 仅设置2个线程, 强制其实现复用
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Supplier<String> sleep3Supplier = () -> {
            System.out.println("开始 -> 异步睡3s线程: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束 -> 异步睡3s线程: " + Thread.currentThread().getName());
            return "sleep 3s";
        };

        Supplier<String> sleep5Supplier = () -> {
            System.out.println("开始 -> 异步睡5s线程: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束 -> 异步睡5s线程: " + Thread.currentThread().getName());
            return "sleep 5s";
        };

        Supplier<String> sleep17Supplier = () -> {
            System.out.println("开始 -> 异步睡17s线程: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束 -> 异步睡17s线程: " + Thread.currentThread().getName());
            return "sleep 17s";
        };

        CompletableFuture<String> completableFuture17 = CompletableFuture.supplyAsync(sleep17Supplier, executorService);
        CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(sleep5Supplier, executorService);
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(sleep3Supplier, executorService);

        // 使用普通complete
        // 使用completeAsync
        completableFuture17.whenComplete((s, throwable) -> {
                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
                }
        );
        completableFuture5.whenComplete((s, throwable) -> {
                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
                }
        );
        completableFuture3.whenComplete((s, throwable) -> {
                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
                }
        );
//
//        // 使用completeAsync
//        completableFuture17.whenCompleteAsync((s, throwable) -> {
//                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
//                }
//        );
//        completableFuture5.whenCompleteAsync((s, throwable) -> {
//                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
//                }
//        );
//        completableFuture3.whenCompleteAsync((s, throwable) -> {
//                    System.out.println("回调线程为: " + Thread.currentThread().getName() + "得到值为: " + s);
//                }
//        );

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
