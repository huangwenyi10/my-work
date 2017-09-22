package com.example.interview.test;

/**
 * @author Ay
 * @date   2017/9/22.
 */
public class ValueReferenceTest {
        public static void main(String[] args) {
            //内存划分为栈内存(小)和堆内存(大)
            // 值类型
            int i = 10;//保存在栈内存
            int m = i;//把值复制给m
            i = 11;
            System.out.println("m=" + m + "   /  " + "i=" + i);
            //引用类型；除了基本数据类型，数组，String，类
            //实际内容存在堆，栈里只存对应到堆的地址
            int[] a = new int[]{1,2,3,4};
            int[] b = a;//把对应到堆的地址给了b
            a[2] = 100;
            System.out.println("b[2]=" + b[2]);
        }

}
