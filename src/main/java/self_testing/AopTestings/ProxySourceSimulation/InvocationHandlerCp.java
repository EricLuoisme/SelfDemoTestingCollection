package self_testing.AopTestings.ProxySourceSimulation;

import java.lang.reflect.Method;

public interface InvocationHandlerCp {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
