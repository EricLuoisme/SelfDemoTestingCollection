package self_testing.Concurrency.CompletableFutureTesting.SimpleTest;


import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTesting {

    public static void main(String[] args) {


        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

//        CustomRecursiveAction abc = new CustomRecursiveAction("workworkwork");
//        forkJoinPool.invoke(abc);

        CustomRecursiveTask efg = new CustomRecursiveTask(new int[]{34, 12, 37, 24});
//        Object invoke = forkJoinPool.invoke(efg);
//        System.out.println(invoke);

        forkJoinPool.execute(efg);
        try {
            // 使用execute提交任务时, 如果不用join, 主线程关闭任务也不执行了
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
