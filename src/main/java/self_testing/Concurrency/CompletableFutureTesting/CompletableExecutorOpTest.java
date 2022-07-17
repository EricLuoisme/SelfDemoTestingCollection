package self_testing.Concurrency.CompletableFutureTesting;

import java.util.concurrent.*;

public class CompletableExecutorOpTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 仅设置2个线程, 强制其实现复用
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 这里需要单独拿出来设置, 否则会出现get不到内容的问题
        Callable<String> callable5 = () -> {
            System.out.println("睡5s线程 -> 开始: " + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("睡5s线程 -> 结束: " + Thread.currentThread().getName());
            return "sleep 5s";
        };
        Callable<String> callable10 = () -> {
            System.out.println("睡10s线程 -> 开始: " + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(10);
            System.out.println("睡10s线程 -> 结束: " + Thread.currentThread().getName());
            return "sleep 10s";
        };

        // 正如结果所见, 这里都只能同步进行
        // 所以main结束一定是在它们都执行完毕, 才能获取
        Future<?> futureTask5 = executorService.submit(callable5);
        Future<?> futureTask10 = executorService.submit(callable10);
        System.out.println(futureTask5.get());
        System.out.println(futureTask10.get());
        System.out.println("main结束");


    }
}
