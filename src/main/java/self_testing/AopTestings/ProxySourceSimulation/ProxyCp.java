package self_testing.AopTestings.ProxySourceSimulation;


import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ProxyCp {

    public static final String ln = "\r\n";

    public static Object newProxyInstance(ClassLoaderCp loader,
                                          Class<?>[] interfaces,
                                          InvocationHandlerCp h) {
        try {
            // 1. 生成源码
            String src = generateSrc(interfaces);

            // 2. 写文件$Proxy0.java
            String filePath = ProxyCp.class.getResource("").getPath();
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();

            // 3. 把java文件编译为class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(f);

            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            // 4. 生成的class文件加载到JVM中
            Class proxyClass = loader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(InvocationHandlerCp.class);


            // 5. 返回新的代理对象
            return c.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append("Self_Testing.AopTestings.ProxySourceSimulation;" + ln)
                .append("Self_Testing.AopTestings.ProxyTesting.IPerson;" + ln)
                .append("import java.lang.reflect.*;" + ln)
                .append("public final class $Proxy0 implements " + interfaces[0].getName() + "{" + ln)
                .append("InvocationHandlerCp h;" + ln)
                .append("public $Proxy0(InvocationHandlerCp h) { " + ln)
                .append("this.h = h;" + ln)
                .append("}" + ln);

        for (Method method : interfaces[0].getMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            sb.append("public " + method.getReturnType().getName() + " " + method.getName() + "() {" + ln)
                    .append("try { " + ln)
                    .append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + method.getName() + "\", new Class[]{});" + ln)
                    .append("this.h.invoke(this, m, new Object[]{});" + ln)
                    .append("return;")
                    .append("}" + ln)
                    .append("catch(Error _ex) { }" + ln)
                    .append("catch(Throwable throwable)" + ln)
                    .append("{" + ln)
                    .append("throw new UndeclaredThrowableException(throwable);" + ln)
                    .append("}" + ln);
        }

        sb.append("}" + ln);
        return null;
    }
}
