package com.yang.me.healthy;

import android.app.Instrumentation;
import android.util.SparseArray;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class test111 {

    class CStack {
        private SparseArray<Object> array = new SparseArray<>();
        private Lock mLock = new ReentrantLock();
        private int index;

        public void push(Object o) {
            try {
                log("尝试获得锁");
                mLock.lock();
                log("获得了锁");
                log("开始push");
                array.put(index, o);
                index++;
                Thread.sleep(500);
                log("结束push");
            } catch (Exception e) {
                log("ex:" + e.getMessage());
                e.printStackTrace();
            } finally {
                mLock.unlock();
                log("释放锁");
            }
        }

        private void log(String msg) {
            System.out.println(Thread.currentThread().getName() + ":" + msg);
        }
    }


    @Test
    public void testLock() {
        final CStack stack = new CStack();
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    stack.push(new Object());
                }
            }, i + "").start();
        }

    }

    @Test
    public void test11() {
        System.out.println(getPrettyFansNum(100));
        System.out.println(getPrettyFansNum(10000));
        System.out.println(getPrettyFansNum(10001));
        System.out.println(getPrettyFansNum(11001));
        System.out.println(getPrettyFansNum(19901));
        System.out.println(getPrettyFansNum(19501));
        System.out.println(getPrettyFansNum(19401));
        System.out.println(getPrettyFansNum(19441));
        System.out.println(getPrettyFansNum(19451));
        System.out.println(getPrettyFansNum(20010));
    }

    public static String getNoZeroTrailNum(Object val) {
        return new BigDecimal(String.valueOf(val)).stripTrailingZeros().toPlainString();
    }

    public static String getPrettyFansNum(int fansCount) {
        float tenThousandValue = fansCount * 1f / 10000;
        if ((int) tenThousandValue < 1) {
            return String.valueOf(fansCount);
        } else {
            String value;
            if ((int) tenThousandValue == tenThousandValue) {
                value = String.valueOf((int) tenThousandValue);
            } else {
                BigDecimal bd = new BigDecimal(tenThousandValue);
                float f = bd.setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                value = String.valueOf(getNoZeroTrailNum(f));
            }
            return String.format(Locale.CHINA, "%1$sw", value);
        }
    }

    class Person {

        private String name;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testPerson() {
        String name = "initial";
        setName(name);
        System.out.println(name);

        Person person = new Person();
        person.name = "xm";
        changePerson(person);
        System.out.println(person);


        Person p2 = new Person();
        p2.name = "p2";
        formatPerson(p2);
        System.out.println(p2);
    }

    public void setName(String name) {
        name = "1111";
    }

    public void changePerson(Person p) {
        p.name = "changed";
    }

    public void formatPerson(Person p) {
        p = new Person();
        p.name = "format";
    }

}
