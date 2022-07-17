package self_testing.Concurrency.CompletableFutureTesting.SimpleTest;

import java.util.concurrent.*;

public class ScheduleThreadPoolExecutorTest {
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(3);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        ScheduledFuture<?> count_once = executorService.scheduleAtFixedRate(() -> {
            System.out.println("count once");
            latch.countDown();
        }, 2, 3, TimeUnit.SECONDS);

        try {
            // 指等待5m, 查看CountDownLatch是否减到0, 如果不触发await, 将不会开启ScheduledTask,
            // 相当于当前线程自己往下执行, 不在等待CountDownLatch
            latch.await(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 如果不调用cancel, 会一直执行
        count_once.cancel(true);


    }
}
