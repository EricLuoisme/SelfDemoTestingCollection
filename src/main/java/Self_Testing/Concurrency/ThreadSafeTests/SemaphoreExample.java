package Self_Testing.Concurrency.ThreadSafeTests;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {

    static class Car extends Thread {
        private int num;
        private Semaphore semaphore;

        public Car(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("A - 抢到 - 第" + num + "令牌");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("R - 释放 - 第" + num + "令牌");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < 20; i++) {
            new Car(i, semaphore).start();
        }


    }
}
