package Self_Testing.Concurrency.ThreadSafeTests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBlockQueueImpl {

    private List<String> items;

    private volatile int size;
    private volatile int count;

    private Lock lock = new ReentrantLock();

    // 多个队列处理不同线程的休眠/唤醒的条件
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public ConditionBlockQueueImpl(int count) {
        this.count = count;
        this.items = new ArrayList<>();
        this.size = 0;
    }

    public void put(String item) {
        lock.lock();
        try {
            if (size >= count) {
                System.out.println("Queue is full, need to wait");
                notFull.await();
            }
            size++;
            items.add(item);
            StringBuilder all = new StringBuilder();
            for (String s : items) {
                all.append(s + " ");
            }
            System.out.println("Queue has: " + all.toString() + "size is: " + size);
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take() {
        lock.lock();
        try {
            if (size <= 0) {
                System.out.println("Queue is empty, need to wait");
                notEmpty.await();
            }
            --size;
            System.out.println("current size + " + size);
            String remove = items.remove(0);
            System.out.println("Get " + remove);
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ConditionBlockQueueImpl test = new ConditionBlockQueueImpl(2);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        threadPool.execute(() -> test.put("a"));
        threadPool.execute(() -> test.put("b"));
        threadPool.execute(() -> test.put("c"));
        threadPool.execute(test::take);
        threadPool.execute(() -> test.put("d"));
        threadPool.execute(test::take);
        threadPool.execute(test::take);
        threadPool.execute(test::take);
        threadPool.execute(test::take);
    }
}
