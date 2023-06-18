package com.yang.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import sun.misc.ProxyGenerator;

public class DynamicProxyTest {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImplDefault();
        calculator.plus(2, 3);


        System.out.println("------------");
        Object targetProxy1 = new MyProxy(new CalculatorImpl()).getTargetProxy();
        System.out.println("--- " + (((Calculator) targetProxy1)).mul(2, 3));

        System.out.println("------start ProxyGenerator------");

        String myClass = "xcyProxyClass";
        byte[] bytes = null;//ProxyGenerator.generateProxyClass(myClass, new Class[]{Calculator.class});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("D:\\" + myClass + ".class"));
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
