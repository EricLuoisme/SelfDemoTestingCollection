package self_testing.Concurrency.CompletableFutureTesting.SimpleTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {

    private static final int THRESHOLD = 4;

    private String workload = "";

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }


    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workload);
        }
    }

    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subTasks = new ArrayList<>();

        String partBefore = workload.substring(0, workload.length() / 2);
        String partAfter = workload.substring(workload.length() / 2);

        subTasks.add(new CustomRecursiveAction(partBefore));
        subTasks.add(new CustomRecursiveAction(partAfter));

        return subTasks;
    }

    private void processing(String work) {
        String result = work.toUpperCase(Locale.ROOT);
        System.out.println(result);
    }

}
