package Self_Testing.AopTestings.ProxyTesting;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyForPerson implements MethodInterceptor {

    /**
     * 任何的class, 都不需要通过接口来实现动态代理, 直接传入class类型即可
     * @param clazz class类型
     * @return 代理类
     */
    public Object getInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        // 相当于增强的Proxy属于目标类的子类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        // 通过enhancer的创建, 返回Proxy对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object o1 = methodProxy.invokeSuper(o, objects);
        after();
        return o1;
    }

    private void before() {
        System.out.println("Who's there?");
    }

    private void after() {
        System.out.println("Ok");
    }
}
