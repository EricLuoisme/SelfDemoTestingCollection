package self_testing.Concurrency.ThreadSafeTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatNotSafeTest {

    /**
     * 非线程安全的
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 线程安全的
     */
    private static final ThreadLocal<SimpleDateFormat> sdf_threadSafe =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    public static Date parse(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    public static Date parseSafe(String strDate) throws ParseException {
        return sdf_threadSafe.get().parse(strDate);
    }

    private void threadLocalTest() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        System.out.println(threadLocal.get());

        String notGc = "123";
        System.out.println(notGc);
    }


    public static void main(String[] args) {

//        ExecutorService threadPool = Executors.newFixedThreadPool(10);
//
//        for (int i = 0; i < 20; i++) {
//            threadPool.execute(() -> {
//                try {
////                    System.out.println(parse("2021-11-09 09:27:20"));
//                    System.out.println(parseSafe("2021-11-09 09:27:20"));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            });
//        }

        SimpleDateFormatNotSafeTest test = new SimpleDateFormatNotSafeTest();
        test.threadLocalTest();


    }


}
