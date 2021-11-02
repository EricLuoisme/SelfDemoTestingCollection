package Self_Testing.Concurrency.CompletableFutureTesting.SimpleTest;


import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTesting {

    public static void main(String[] args) {


        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        CustomRecursiveAction abc = new CustomRecursiveAction("workworkwork");

        forkJoinPool.invoke(abc);
    }
}
