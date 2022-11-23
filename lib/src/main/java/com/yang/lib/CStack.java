package com.yang.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.SimpleFormatter;

class CStack {
    private List<Object> array = new ArrayList<>();
    private Lock mLock = new ReentrantLock();
    private int index;

    public void push(Object o) {
            try {
                log("尝试获得锁");
                mLock.lockInterruptibly();
                log("获得了锁");
                log("开始push");
                array.add(index, o);
                index++;
                Thread.sleep(1000);
                log("结束push");
            } catch (Exception e) {
                log("ex:" + e.getMessage());
                e.printStackTrace();
            } finally {
                mLock.unlock();
                log("释放锁");
            }
    }

    public static void log(String msg) {
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateStr = simpleFormatter.format(new Date());
        System.out.println(dateStr + " thread[" + Thread.currentThread().getName() + "]:" + msg);
    }

    static class Task implements Runnable {

        private Lock lock = new ReentrantLock(true);

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    Thread.sleep(1000);
                    log(" i got lock");
                } catch (Exception e) {
                    log("ex:" + e.getMessage());
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }



    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task, "1").start();
        new Thread(task, "2").start();
        new Thread(task, "3").start();


//        final CStack stack = new CStack();
//        for (int i = 0; i < 4; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    stack.push(new Object());
//                }
//            }, i + "").start();
//        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                stack.push(new Object());
//            }
//        }, 1 + "").start();
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                stack.push(new Object());
//            }
//        }, 2 + "");
//        thread2.start();
//        thread2.interrupt();
    }

}

