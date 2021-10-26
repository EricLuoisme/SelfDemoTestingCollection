package Self_Testing.Concurrency;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class HappensBeforeTest {

    private int a = 1;
    private volatile int b = 2;


    public void write() {
        this.a = 3;
        this.b = this.a;
    }

    public void read() {
        System.out.println("b=" + this.b + ", a=" + this.a);
    }

    public static void main(String[] args) {

        while (true) {
            HappensBeforeTest ins = new HappensBeforeTest();
            // Thread_1
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ins.write();
                }
            }).start();
            // Thread_2
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ins.read();
                }
            }).start();
        }
//        Class

    }
}
