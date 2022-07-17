package self_testing.AopTestings.ProxySourceSimulation;

import self_testing.AopTestings.ProxyTesting.IPerson;
import self_testing.AopTestings.ProxyTesting.Tom;

public class RunningStuffOnSimulation {
    public static void main(String[] args) {
        /**
         * JDK反射版实现代理
         */
        JdkCpProxyForPerson helper = new JdkCpProxyForPerson();
        IPerson instance = helper.getInstance(new Tom());
        instance.answer();


    }
}
