package Self_Testing.ProxyTesting;

import org.springframework.cglib.core.DebuggingClassWriter;

public class RunningStuff {
    public static void main(String[] args) {
        /**
         * JDK反射版实现代理
         */
//        JdkProxyForPerson helper = new JdkProxyForPerson();
//        IPerson instance = helper.getInstance(new Tom());
//        instance.answer();
//
//
//
//        System.out.println();
//        IPerson instance1 = helper.getInstance(new Jerry());
//        instance1.answer();

        /**
         * cglib版代理
         */
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\cglibTest");
        CglibProxyForPerson cglibProxyForPerson = new CglibProxyForPerson();
        Jerry jerry = (Jerry) cglibProxyForPerson.getInstance(Jerry.class);
        jerry.answer();


    }
}
