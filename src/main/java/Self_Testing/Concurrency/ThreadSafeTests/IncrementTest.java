package Self_Testing.Concurrency.ThreadSafeTests;

import org.springframework.util.StopWatch;

public class IncrementTest {

    int i = 0;

    public synchronized void incr() {
        this.i++;
    }

    public static void main(String[] args) throws InterruptedException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        IncrementTest test = new IncrementTest();

        Thread[] threads = new Thread[2];

        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    test.incr();
                }
            });
            threads[i].start();
        }

        threads[0].join();
        threads[1].join();

        stopWatch.stop();
        System.out.println("StopWatch '': running time = 16392600 ns");
        System.out.println(stopWatch.prettyPrint());
        System.out.println(test.i);
    }

}
