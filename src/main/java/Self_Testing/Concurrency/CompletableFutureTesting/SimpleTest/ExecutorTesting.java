package Self_Testing.Concurrency.CompletableFutureTesting.SimpleTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorTesting {

    public static void main(String[] args) {

        // 函数式编程声明一个Runnable的实例
        Runnable runnableTask = () -> {
            try {
                System.out.println("Runnable Task Run");
                TimeUnit.MILLISECONDS.sleep(3000);
                System.out.println("Runnable Task Finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 函数式编程声明一个Callable的实例
        Callable<String> callableTask_1 = () -> {
            System.out.println("Callable Task 1 Run");
            TimeUnit.MILLISECONDS.sleep(3300);
            System.out.println("Callable Task 1 Finished");
            return "Callable Task 1 Completed";
        };

        // 函数式编程声明一个Callable的实例
        Callable<String> callableTask_2 = () -> {
            System.out.println("Callable Task 2 Run");
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println("Callable Task 2 Finished");
            return "Callable Task 2 Completed";
        };

        // 函数式编程声明一个Callable的实例
        Callable<String> callableTask_3 = () -> {
            System.out.println("Callable Task 3 Run");
            TimeUnit.MILLISECONDS.sleep(3000);
            System.out.println("Callable Task 3 Finished");
            return "Callable Task 3 Completed";
        };

        List<Callable<String>> callableList = new ArrayList<>();
        callableList.add(callableTask_1);
        callableList.add(callableTask_2);
        callableList.add(callableTask_3);

        // 声明线程池
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(4);

            executorService.execute(runnableTask);

//
            List<Future<String>> futures = executorService.invokeAll(callableList);
//            TimeUnit.MILLISECONDS.sleep(1000);
//            List<Runnable> remainingTasks = executorService.shutdownNow();
//            for (Runnable remainingTask : remainingTasks) {
//                System.out.println(remainingTask.toString());
//            }

            futures.forEach(x -> {
                try {
                    String s = x.get();
                    System.out.println(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
