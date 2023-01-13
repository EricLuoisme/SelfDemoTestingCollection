package self_testing.AopTestings.ProxyTesting;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CglibProxyForPerson implements MethodInterceptor {

    /**
     * 任何的class, 都不需要通过接口来实现动态代理, 直接传入class类型即可
     *
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

        // get name of proxied class's name
        String enhancedName = o.getClass().getSimpleName();
        String[] split = enhancedName.split("\\$\\$");
        System.out.println("[Proxy_" + split[0] + "] input on [" + method.getName() + "] with params:[" + JSONObject.toJSONString(objects) + "]");
        Object o1 = methodProxy.invokeSuper(o, objects);
        return o1;
    }

}
