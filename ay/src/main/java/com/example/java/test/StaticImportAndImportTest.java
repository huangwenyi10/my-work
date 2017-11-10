package com.example.java.test;


import static org.junit.Assert.assertTrue;
//import static java.lang.Integer.MAX_VALUE;
import static java.lang.Long.MAX_VALUE;
import org.junit.Assert;
import org.junit.Test;
import static java.lang.Math.PI;

/**
 * 描述：静态导入 和导入 的区别
 * @author Ay
 * @date   2017-11-08
 */
public class StaticImportAndImportTest {

    //静态导入的运用：
    /**
     * 使用普通导入
     * @param productId
     */
    @Test
    public void test(Integer productId){

        Assert.assertTrue(productId >= 0);
        Assert.assertTrue(productId != null);

    }


    /**
     * 使用静态导入
     * @param productId
     */
    @Test
    public void test2(Integer productId){

        assertTrue(productId >= 0);
        assertTrue(productId != null);

    }

    /**
     * 错误例子
     * @param productId
     */
    @Test
    public void test3(Integer productId){
        //提防含糊不清的命名static成员
        System.out.println(MAX_VALUE);

    }


    /**
     * 正确运用
     * @param productId
     */
    @Test
    public void test4(Integer productId){
        //1.static方法上进行静态导入
        //assertTrue(productId >= 0);
        //Assert.assertTrue(productId != null);

        //2.常量
        System.out.println(PI);

    }


    //你必须说import static， 不能说static import。
    //提防含糊不清的命名static成员。例如，如果你对Integer类和Long类执行了静态导入，引用MAX_VALUE将导致一个编译器错误，
    // 因为Integer和Long都有一个MAX_VALUE常量，并且Java不会知道你在引用哪个MAX_VALUE。
    //你可以在static对象引用、常量（记住，它们是static 或final）和static方法上进行静态导入。



}
