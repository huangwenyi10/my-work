package com.example.java.test;


import org.junit.Test;

/**
 * return 与 try catch的关系
 * @author Ay
 * @date   2017-10-1
 */
public class ReturnAndTryCatchTest {


    @Test
    public void test() {

        try{

            System.out.println("try");
        }catch (Exception e){

            System.out.println("catch");
        }finally {
            System.out.println("finally");

        }
        System.out.println("method body");
        return;

        //打印结果
        //try
        //finally
        //method body
    }


    @Test
    public void test2() {

        try{
            System.out.println("try");
            String str = null;
            str.split(",");
        }catch (Exception e){

            System.out.println("catch");
            System.out.println("return");
            return;
        }finally {
            System.out.println("finally");

        }
        System.out.println("method body");
        return;

        //打印结果
        //try
        //catch
        //return
        //finally
    }

    @Test
    public void test3() {

        try{
            System.out.println("try");
            System.out.println("try return");
            String str = null;
            str.split(",");

            return;
        }catch (Exception e){

            System.out.println("catch");
            System.out.println("catch return");
            return;
        }finally {
            System.out.println("finally");
            return;
        }

        //打印结果
        //try
        //try return
        //catch
        //catch return
        //finally
    }

    @Test
    public void test4() {


        try{
            System.out.println("try");
            System.out.println("try return");
            return;
        }catch (Exception e){

            System.out.println("catch");
            System.out.println("catch return");
            return;
        }finally {
            System.out.println("finally");
            return;
        }

        //打印结果
        //try
        //try return
        //finally
    }

    @Test
    public void test5(){

        System.out.println("最终打印的值是：" + returnTest());
    }

    private String returnTest(){
        String msg = "";

        try{
            msg = "try";
            return msg;
        }catch (Exception e){

            msg = "catch";
            return msg;
        }finally {
            msg = "finally";
            //return msg; //1
        }

        //打印结果
        //最终打印的值是：finally

        //注释掉 1 打印结果
        //最终打印的值是：try

        //结论
        //如果finally语句中没有return语句覆盖返回值，那么原来的保存下来的返回值不一定会因为finally里的修
    }

    //结论
    //任何执行try 或者catch中的return语句之前，都会先执行finally语句，如果finally存在的话。
    //如果finally中有return语句，那么程序就return了，所以finally中的return是一定会被return的，
    //编译器把finally中的return实现为一个warning。




}

//### 参考文章
//【1】http://www.cnblogs.com/fery/p/4709841.html
//

