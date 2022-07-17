package self_testing.Concurrency.ThreadSafeTests;

import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    private static final Integer MAX = 5000;

    static class CalForJoinTask extends RecursiveTask<Integer> {
        private Integer startVal;
        private Integer endVal;

        public CalForJoinTask(Integer startVal, Integer endVal) {
            this.startVal = startVal;
            this.endVal = endVal;
        }

        @Override
        protected Integer compute() {
            // 任务足够小就开始计算
            if (endVal - startVal < MAX) {
                Integer totalVal = 0;
                for (int i = startVal; i <= endVal; i++) {
                    totalVal += i;
                }
                return totalVal;
            }
            // 拆分子任务
            CalForJoinTask subA = new CalForJoinTask(startVal, (startVal + endVal) / 2);
            subA.fork();
            CalForJoinTask subB = new CalForJoinTask((startVal + endVal) / 2 + 1, endVal);
            subB.fork();
            // 回归总任务
            return subA.join() + subB.join();
        }
    }

    public static void main(String[] args) {

        StopWatch watch = new StopWatch();
        watch.start();

        CalForJoinTask task = new CalForJoinTask(0, 100000);
        // ForkJoinPool也是一个线程池, 它内部每个线程分WorkQueue, 这个队列是Deque, 所以执行完成的队列可以
        // 使用窃取方式, 把另外的仍在排队的task窃取过来执行
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> future = pool.submit(task);

        try {
            Integer integer = future.get();
            System.out.println("Total result = " + integer);
            watch.stop();
            System.out.println(watch.prettyPrint());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println();

        // 使用原始单个线程计算速度慢很多
        watch.start();
        Integer theResult = 0;
        for (int i = 0; i < 100000; i++) {
            theResult += i;
        }
        System.out.println("Total result = " + theResult);
        watch.stop();
        System.out.println(watch.prettyPrint());

    }


}
