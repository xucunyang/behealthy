package com.yang.lib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class MyProxy {

    private final Object mTarget;

    public MyProxy(Object target) {
        this.mTarget = target;
    }

    public Object getTargetProxy() {
        ClassLoader classLoader = mTarget.getClass().getClassLoader();
        Class<?>[] interfaces = mTarget.getClass().getInterfaces();
        Object o = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("----> before: " + method.getName() + ", " + Arrays.toString(args));
                Object result = method.invoke(mTarget, args);
                System.out.println("-----> after: " + result);

                return result;
            }
        });
        return o;

    }
}
