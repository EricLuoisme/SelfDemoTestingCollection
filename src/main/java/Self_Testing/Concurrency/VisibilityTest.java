package Self_Testing.Concurrency;

public class VisibilityTest {
    public static void main(String[] args) {

        // 子线程
        TestThread t1 = new TestThread();
        t1.start();

        // 主线程
        while (true) {
            if (t1.getFlag()) {
                System.out.printf("Entering Main Thread");
            }
        }

    }
}

class TestThread extends Thread {

    private boolean flag = false;


    @Override
    public void run() {
        try {
            // 让主线程有机会优先执行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("Thread set flag to " + flag);
    }

    public boolean getFlag() {
        return this.flag;
    }
}
