package com.yang.myapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5,
                30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));
        executor.allowCoreThreadTimeOut(true);
//        for (int i = 1; i < 4; i++) {
//            log("curr index " + i);
        executor.execute(() -> {
            int curr = 1;
            while (curr < 50) {
                try {
                    log("[" + Thread.currentThread().getName() + "] curr -> " + curr + " start");
                    sleep(1000);
                    // 简单地打印当前线程名称
                    log("[" + Thread.currentThread().getName() + "] curr -> " + curr + " end");
                } catch (Exception e) {
//                        e.printStackTrace();
                    log("ex msg: " + e.getMessage());
                }
                curr++;
            }
        });
//        }

        sleep(3100);
        log("shutdown =============== ");
        executor.shutdown();
    }

    class Fruit {
        public void print() {
            System.out.println("fruit");
        }
    }

    class Apple extends Fruit {
        public void print() {
            System.out.println("Apple");
        }
    }

    class Orange extends Fruit {
        public void print() {
            System.out.println("Orange");
        }
    }


    @Test
    public void genericsTest() throws Exception {
//        List<? extends Fruit> fruits = new ArrayList<>();
//        fruits.add(new Fruit());
//        fruits.add(new Apple());
//        fruits.add(new Orange());

        ArrayList<Fruit> fruits1 = new ArrayList<>();
        genericsT1(fruits1);
        for (Fruit fruit : fruits1) {
            fruit.print();
//            System.out.println("--> " + );
        }
    }

    //定义一个Person类
    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getNmae() {
            return this.name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    // 定义一个Student 并继承 Person
    static class Student extends Person {
        Student(String name) {
            super(name);
        }
    }

    @Test
    public void testGenerics2() {

        ArrayList<Person> a1 = new ArrayList<Person>();
        a1.add(new Person("abc1"));
        a1.add(new Person("abc2"));
        a1.add(new Person("abc3"));

        printMethod(a1);


        // 下面是错误的。a2存的是Person，存在继承的话，也能放worker。但是等号右边只能存Student，存不进worker.类型安全问题。左右两边要一致
        ArrayList<Student> a2 = new ArrayList<Student>();
        a2.add(new Student("abc--1"));
        a2.add(new Student("abc--2"));
        a2.add(new Student("abc--3"));
        printMethod(a2); // ArrayList<Person> a2 = new ArrayList<Student>();
        //如果我想调用也printMethod(a2);没毛病。怎么做？。 第一想法直接给占位符。但是带来一问题，不能调用具体方法。
    }

    // 与main方法同级的静态工具类方法
    public static void printMethod(ArrayList<? super Student> a1) {
        Iterator<? super Student> it = a1.iterator();
//        a1.add(new Student(""));
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void sop(ArrayList<?> arrayList) {
        for (Iterator<?> it = arrayList.iterator(); it.hasNext(); ) {
            Object next = it.next();
        }
        arrayList.add(null);
//         arrayList.add("111");
    }

    public void genericsT1(List<Fruit> fruits) throws Exception {
//        List<? extends Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit());
        fruits.add(new Apple());
        fruits.add(new Orange());
    }


    @Test
    public void synchronizedTest() throws Exception {
        log("start");
        ReentrantLock fairLock = new ReentrantLock(false);
//        ReentrantLock unFairLock = new ReentrantLock(false);

        for (int i = 0; i < 20; i++) {
            int finalI = i;
//            sleep(10 * finalI);
            new Thread(() -> {
                try {
                    log("--------------- > start :" + finalI);
//                    sleep(10 * finalI);
                    fairLock.lock();
                    log("--------------- > get lock :" + finalI);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
                } finally {
                    fairLock.unlock();
                }
            }).start();
        }
        log("end");
    }

    private void test(int index) {
        log("--------------- > Task :" + index);
    }

    public void log(String msg) {
        String time = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS").format(new Date());
        System.out.println(time + ": " + msg);
    }


    @Test
    public void sort() throws InterruptedException {
        int[] array = {2, 25, 12, 20, 30, 3, 1, 11};
        printImage(array, -1);
        int startIndex = 0;
        for (int i = startIndex; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
//                    System.out.println("change array[" + i + "] ->  array[" + j + "]");
//                    System.out.println("change        " + array[i] + " ->  " + array[j]);
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
                printImage(array, i);
            }

//            System.out.println("end i=" + i + " loop");
//            System.out.println("  ");
            startIndex++;
        }
        printImage(array, -1);
    }

    private void printImage(int[] arr, int curr) {
        for (int i = 0; i < arr.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arr[i]; j++) {
                sb.append("▉");
                if (j == arr[i] - 1) {
                    sb.append(arr[i]);
                    if (curr != -1 && curr == i) {
                        sb.append("*");
                    }
                }
            }
            System.out.println(sb.toString());
        }
        try {
            sleep(1000);
            clearnCmd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printImageVertical(int[] arr, int curr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        for (int i = 0; i < arr.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arr[i]; j++) {
//                if (max )
                sb.append("▉");
                if (j == arr[i] - 1) {
                    sb.append(arr[i]);
                    if (curr != -1 && curr == i) {
                        sb.append("*");
                    }
                }
            }
            System.out.println(sb.toString());
        }
        try {
            sleep(1000);
            clearnCmd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clearnCmd() {
        try {//使用命令的过程可能会出现失败，需要捕获异常
            //   Process process = Runtime.getRuntime().exec("cls");
            new ProcessBuilder("cmd", "/c", "cls")
                    // 将 ProcessBuilder 对象的输出管道和 Java 的进程进行关联，这个函数的返回值也是一个
                    // ProcessBuilder
                    .inheritIO()
                    // 开始执行 ProcessBuilder 中的命令
                    .start()
                    // 等待 ProcessBuilder 中的清屏命令执行完毕
                    // 如果不等待则会出现清屏代码后面的输出被清掉的情况
                    .waitFor(); // 清屏命令
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLink() {
        Link headerLink = null, currLink = null, nextLink;
        int endIndex = 10;
        for (int i = 0; i < endIndex; i++) {
            if (i == 0) {
                currLink = new Link("link " + i);
                headerLink = currLink;
            }

            if (i != endIndex - 1) {
                nextLink = new Link("link " + (i + 1));
            } else {
                nextLink = null;
            }
            currLink.next = nextLink;
            currLink = nextLink;
        }

        printLink(headerLink);

        reverseLink(headerLink);
    }

    private void printLink(Link headerLink) {
        Link temp = headerLink;
        do {
            log("---->> " + temp);
            temp = temp.next;
        }
        while (temp != null);
    }

    public void reverseLink(Link head) {
        Link cur = head;
        Link pre = null;
        Link tempHeader = null;
        while (true) {
            tempHeader = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tempHeader;
            if (cur.next == null) {
                cur.next = pre;
                break;
            }
        }
        head.next = null;
        log("start-----------");
//        printLink(tempHeader);
        reverseList(tempHeader);
        log("end-----------");
    }

    public void reverseList(Link head) {
        Link cur = head.next;
        head.next = null;
        while (cur != null) {
            Link currNext = cur.next;
            cur.next = head;
            head = cur;
            cur = currNext;
        }
        log("start-----------");
        printLink(head);
        log("end-----------");
    }

    static class Link {
        public Object object;
        public Link next;

        public Link(Object o) {
            this.object = o;
        }

        public Link(Object o, Link next) {
            this.object = o;
            this.next = next;
        }

        public String toString() {
            return this.object.toString() + " next " + (next == null ? "is null" : next.object);
        }

        public static void main(String[] args) {


        }

        public void reverseLinkList(Link header) {

        }
    }


}
