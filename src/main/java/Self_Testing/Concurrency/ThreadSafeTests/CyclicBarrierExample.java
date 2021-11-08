package Self_Testing.Concurrency.ThreadSafeTests;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample {


    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("写入完毕, 等待穿越");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("该线程穿越");
        }
    }


    public static void main(String[] args) {
        int n = 4;
        // 注意, 这里的Runnable传入, 都是先执行传入的Runnable(下面睡眠2s的测试可以看出), 然后才是唤醒Thread
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程穿越完毕");
        });
        for (int i = 0; i < n; i++) {
            new Writer(barrier).start();
        }

    }
}
