package com.yang.myapplication.algorithm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUt {

    @Test
    public void fun1() throws InterruptedException {
        printFileCombination(slideWindow(12));
        printFileCombination(slideWindow(13));
        printFileCombination(slideWindow(25));
        printFileCombination(fileCombination(12));
        printFileCombination(fileCombination(13));
        printFileCombination(fileCombination(25));
    }

    private void printFileCombination(int[][] ints) {
        System.out.println("-----start------");
        for (int[] anInt : ints) {
            System.out.println("----------- to string " + Arrays.toString(anInt));
        }
        System.out.println("-----end------");
    }

    public int[][] fileCombination(int target) {
        ArrayList<int[]> list = new ArrayList<>();
        int i = 1;
        double j = 2.0;
        while (i < j) {
            j = (-1 + Math.sqrt(4 * (2 * target + i * i - i) + 1)) / 2;
            if (i < j && j == (int) j) {
                int[] arr = new int[(int) (j - i + 1)];
                for (int k = i; k <= j; k++) {
                    arr[k - i] = k;
                }
                list.add(arr);
            }
            i++;
        }
        return list.toArray(new int[0][]);
    }

    /**
     * stretch caterpillar 伸缩毛毛虫
     *
     * @param target target
     * @return target
     */
    public int[][] slideWindow(int target) {
        ArrayList<int[]> list = new ArrayList<>();
        int i = 1;
        int j = 2;
        int sum;
        while (i < j) {
            sum = (j + i) * (j - i + 1)/ 2;
            if (sum == target) {
                int[] arr = new int[j - i + 1];
                for (int k = i; k <= j; k++) {
                    arr[k - i] = k;
                }
                list.add(arr);
            }

            if(sum >= target) {
                i ++;
            } else {
                j ++;
            }
        }
        return list.toArray(new int[0][]);
    }
}
