package com.yang.myapplication.aop;

public class RvTest {

    private int testCount = 0;

    public void countPlus() {
        testCount++;
    }

    public void countMinus() {
        testCount--;
    }

    public int getCount() {
        return testCount;
    }

}
