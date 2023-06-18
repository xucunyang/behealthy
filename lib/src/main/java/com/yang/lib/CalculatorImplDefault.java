package com.yang.lib;

public class CalculatorImplDefault implements Calculator {
    @Override
    public int plus(int a, int b) {
        logBefore("plus", new int[]{a, b});
        int result = a + b;
        logAfter("plus", result);
        return result;
    }

    @Override
    public int sub(int a, int b) {
        logBefore("sub", new int[]{a, b});
        int result = a - b;
        logAfter("sub", result);
        return result;
    }

    @Override
    public int mul(int a, int b) {
        logBefore("mul", new int[]{a, b});
        int result = a * b;
        logAfter("mul", result);
        return result;
    }

    @Override
    public int div(int a, int b) {
        logBefore("div", new int[]{a, b});
        int result = a / b;
        logAfter("div", result);
        return result;
    }
}
