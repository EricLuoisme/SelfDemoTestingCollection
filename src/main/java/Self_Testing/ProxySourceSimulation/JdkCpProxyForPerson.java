package Self_Testing.ProxySourceSimulation;

import Self_Testing.ProxyTesting.IPerson;

import java.lang.reflect.Method;

public class JdkCpProxyForPerson implements InvocationHandlerCp {

    private IPerson target;

    public IPerson getInstance(IPerson target) {
        this.target = target;
        Class<? extends IPerson> aClass = target.getClass();
        // 使用反射中的Proxy类, 让其新生成一个Proxy类对象
        return (IPerson) ProxyCp.newProxyInstance(new ClassLoaderCp(), aClass.getInterfaces(), this);
    }

    /**
     * 该方法是InvocationHandler的必要实现, 让Proxy代理对象被调用时, 自行决定是否需要嵌入逻辑
     *
     * @param proxy  proxy实体
     * @param method interface接口的方法
     * @param args   interface接口方法中所需的入参
     * @return 增强后的proxy实体
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 调用方法前的某些增强
        before();
        // 2. 调用方法
        Object invoke = method.invoke(this.target, args);
        // 3. 调用方法后的某些增强
        after();
        return invoke;
    }

    private void before() {
        System.out.println("Who's there?");
    }

    private void after() {
        System.out.println("Ok");
    }
}
