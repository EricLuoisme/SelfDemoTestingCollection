package self_testing.Concurrency.CompletableFutureTesting.SimpleTest;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask {

    private static final int THRESHOLD = 20;

    private int[] arr;

    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }


    @Override
    protected Object compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks()).stream().mapToInt(x -> (int) x.join()).sum();
        } else {
            return processing(arr);
        }
    }


    private Collection<CustomRecursiveTask> createSubTasks() {
        List<CustomRecursiveTask> subTasks = new ArrayList<>();

        subTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        subTasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));

        return subTasks;
    }

    private Integer processing(int[] arr) {
        int sum = Arrays.stream(arr).filter(a -> a > 10 && a < 26).map(a -> a * 10).sum();
        System.out.println(sum);
        return sum;
    }


}
