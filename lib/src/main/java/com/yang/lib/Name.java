package com.yang.lib;

public class Name {

    static int count = 0;

    static {
        count ++;
        System.out.println("Name Class Loaded! count = [" + count + "]" );
    }

    public Name() {
        System.out.println("Name Constructor called!");
    }
}
