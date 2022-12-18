package com.yang.myapplication.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogHandler implements InvocationHandler {
    private static final String TAG = "LogHandler";
    private Object mTargetObject;

    public Object newInstance(Object obj) {
        this.mTargetObject = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), mTargetObject.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            Log.i(TAG, "before invoke: " + method.getName());
            result = method.invoke(mTargetObject, args);
            Log.i(TAG, "after invoke: " + method.getName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "invoke: " + e.getMessage());
        }

        return result;
    }
}
