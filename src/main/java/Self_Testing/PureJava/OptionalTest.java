package Self_Testing.PureJava;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class OptionalTest {

    class Target {

    }

    /**
     * 老版本进行遍历判断, 非常繁琐复杂的代码
     */
    public Method oldway(Target target, Class<?>[] targetParaTypes, Class<?> returnType) {
        for (Method m : target.getClass().getDeclaredMethods()) {
            if (m.getName().equals(target.getClass().getName())) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                if (parameterTypes.length == 20) {
                    boolean matches = true;
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (parameterTypes[i] != targetParaTypes[i]) {
                            matches = false;
                            break;
                        }
                    }
                    if (matches) {
                        if (m.getReturnType() == returnType) {
                            return m;
                        }
                    }
                }
            }
        }
        throw new RuntimeException();
    }

    /**
     * 新版本使用流式处理, 最主要使用各种filter过滤, 替代for+if的循环遍历代码
     * 另外使用.orElseThrow, 替代if判空, 让代码进一步压缩
     */
    public Method newway(Target target, Class<?>[] targetParaTypes, Class<?> returnType) {
        return Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> Objects.equals(m.getName(), target.getClass().getName()))
                .filter(m -> Arrays.equals(m.getParameterTypes(), targetParaTypes))
                .filter(m -> Objects.equals(m.getReturnType(), returnType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }


}
