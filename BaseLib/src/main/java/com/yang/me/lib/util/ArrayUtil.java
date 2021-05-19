package com.yang.me.lib.util;

import java.util.List;

public class ArrayUtil {

    public static boolean isEmpty(List list) {
        return (list == null || list.size() == 0);
    }

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

}