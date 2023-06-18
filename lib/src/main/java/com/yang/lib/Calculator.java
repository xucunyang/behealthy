package com.yang.lib;

import java.util.Arrays;

public interface Calculator {

    int plus(int a, int b);

    int sub(int a, int b);

    int mul(int a, int b);

    int div(int a, int b);

    default void logBefore(String methodName, int[] args) {
        System.out.println("before execute, method name:[" + methodName + "], args:" + Arrays.toString(args));
    }

    default void logAfter(String methodName, int result) {
        System.out.println("before after, method name:[" + methodName + "], result:" + result);
    }

}