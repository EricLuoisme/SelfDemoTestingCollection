package self_testing.Concurrency.ThreadSafeTests;

import java.lang.ref.WeakReference;

public class ReferenceTest {

    static Object obj = new Object();

    public static void main(String[] args) {
        // 强引用(GC之后, 强引用strongRef还是有obj实例的指向)
//        Object strongRef = obj;
//        obj = null;
//        System.gc();
//        System.out.println(strongRef);

        // 弱引用(GC之后, 弱引用会为空)
        WeakReference<Object> objWeakReference = new WeakReference<>(obj);
        obj = null;
        System.gc();
        System.out.println(objWeakReference.get());



    }
}
