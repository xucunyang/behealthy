package com.yang.asmtestmodule;

public class InjectTest {
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    //    @AsmInject
    public static void main(String[] args) throws InterruptedException {
//        long start = System.currentTimeMillis();
        Thread.sleep(2000);
//        long end = System.currentTimeMillis();
//        System.out.println("cost:" + (end - start) + "ms.");
    }

}
