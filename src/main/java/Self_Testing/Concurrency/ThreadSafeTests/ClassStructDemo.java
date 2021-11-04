package Self_Testing.Concurrency.ThreadSafeTests;

import org.openjdk.jol.info.ClassLayout;

public class ClassStructDemo {

    Object o = new Object();

    public static void main(String[] args) {
        ClassStructDemo demo = new ClassStructDemo();
        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
        System.out.println();
        System.out.println(ClassLayout.parseClass(ClassStructDemo.class).toPrintable());
    }
}
