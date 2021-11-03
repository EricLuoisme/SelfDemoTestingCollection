package Self_Testing.Concurrency.CompletableFutureTesting;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableExecutorAsyncTest {

    public static void main(String[] args) {
        // 创建单线程的线程池
        ExecutorService threadPool = Executors.newSingleThreadExecutor(
                r -> new Thread(r, "single thread pool"));

        // 创建一个立刻执行完毕的Task, 这里可以看出, 还没有任何Thread调用whenComplete, 就已经打印出了线程名称
        CompletableFuture<Integer> immediateTask = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " supply");
            return 20;
        }, threadPool);

        // 判断使用Async与否的区别

//        // 使用Async并指定ExecutorPool
//        immediateTask.thenApplyAsync(f -> {
//            System.out.println(Thread.currentThread().getName() + "thenApply");
//            return f.toString();
//        }, threadPool);
//
//        immediateTask.whenCompleteAsync((s, t) -> {
//            System.out.println(Thread.currentThread().getName() + "whenComplete");
//            System.out.println(s);
//        }, threadPool);

        // 不使用Async
        CompletableFuture<String> afterApply = immediateTask.thenApply(f -> {
            System.out.println(Thread.currentThread().getName() + " thenApply");
            return f.toString();
        });

        afterApply.whenComplete((s, t) -> {
            System.out.println(Thread.currentThread().getName() + " whenComplete");
            System.out.println(s);
        });

        System.out.println("Main thread running here");


    }
}
