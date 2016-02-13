package cn.com.caoyue.bihu.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射调用私有方法
 */
public class PrivateMethodInvoker {

    private static Method getMethod(Class objectClass, String methodName, Class[] paramClass) throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = objectClass.getDeclaredMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            if (null == objectClass.getSuperclass()) {
                throw new NoSuchMethodException();
            } else {
                method = getMethod(objectClass.getSuperclass(), methodName, paramClass);
            }
        }
        return method;
    }

    public static Object invoke(Object object, String methodName, Object[] param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class[] paramClass = new Class[param.length];
        for (int i = 0; i < param.length; i++) {
            paramClass[i] = param[i].getClass();
        }
        return invoke(object, methodName, paramClass, param);
    }

    public static Object invoke(Object object, String methodName, Class[] paramClass, Object[] param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getMethod(object.getClass(), methodName, paramClass);
        method.setAccessible(true);
        return method.invoke(object, param);
    }
}
