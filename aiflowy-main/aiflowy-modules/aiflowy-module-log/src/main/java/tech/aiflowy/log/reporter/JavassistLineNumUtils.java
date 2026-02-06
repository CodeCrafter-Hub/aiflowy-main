package tech.aiflowy.log.reporter;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;

import java.util.concurrent.ConcurrentHashMap;

public class JavassistLineNumUtils {

    private static final ClassPool CLASS_POOL = ClassPool.getDefault();
    private static final ConcurrentHashMap<String, Integer> LINE_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取方法在源码中的起始行号
     */
    public static int getLineNumber(java.lang.reflect.Method method) {
        String key = method.getDeclaringClass().getName() + "." + method.getName();
        return LINE_CACHE.computeIfAbsent(key, k -> {
            try {
                CtClass ctClass = CLASS_POOL.get(method.getDeclaringClass().getName());
                CtClass[] params = toCtClasses(method.getParameterTypes());
                CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName(), params);

                MethodInfo methodInfo = ctMethod.getMethodInfo();
                return methodInfo.getLineNumber(0);

            } catch (Exception e) {
                return 0;
            }
        });
    }

    private static CtClass[] toCtClasses(Class<?>[] parameterTypes) throws NotFoundException {
        CtClass[] result = new CtClass[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            result[i] = CLASS_POOL.get(parameterTypes[i].getName());
        }
        return result;
    }
}