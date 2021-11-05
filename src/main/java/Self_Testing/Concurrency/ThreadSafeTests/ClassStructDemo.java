package Self_Testing.Concurrency.ThreadSafeTests;


import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class ClassStructDemo {

    Object o = new Object();

    public static void main(String[] args) {
        ClassStructDemo demo = new ClassStructDemo();
        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        System.out.println("加锁后");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (demo) {
            System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        }
    }
}
