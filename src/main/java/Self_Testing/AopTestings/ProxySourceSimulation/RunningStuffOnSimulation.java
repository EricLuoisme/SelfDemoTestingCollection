package Self_Testing.AopTestings.ProxySourceSimulation;

import Self_Testing.AopTestings.ProxyTesting.IPerson;
import Self_Testing.AopTestings.ProxyTesting.Tom;

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
