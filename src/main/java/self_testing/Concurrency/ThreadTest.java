package self_testing.Concurrency;

import java.util.concurrent.TimeUnit;

public class ThreadTest implements Runnable{

    public static void main(String[] args) {

        Thread t1 = new Thread(new ThreadTest());
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 发出中断信号, 但究竟要不要中断停止, 应该取决于线程它的操作
        t1.interrupt();

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Try sleep");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Finished sleep");
            } catch (InterruptedException e) {
                // 进入到这里, 中断标记首先变为false
                e.printStackTrace();
                // 如果这里不使用interrupt, 线程会复位, 既接受了一次interrupted的异常
                // 然后没有额外处理, interrupted的flag会重置, 所以只会接受到一次interrupted
                wasInterrupted();
                // 这里是把中断标记修改为True
                Thread.currentThread().interrupt();
            }
        }
    }

    private void wasInterrupted() {
        System.out.println("was Interrupted !!!");
    }
}
