package com.example.java.test;

/**
 * 描述：设计模式-模板方法模式
 * @author Ay
 * @date 2017-08-07
 */
public class AyTest {

}

interface A{
    void m1();
    void m2();
    void m3();
}

abstract class B implements A{

    public void m1(){}
    public void m2(){}
    public void m3(){}
}


class C extends B{

    @Override
    public void m1(){
        super.m1();
    }


}

class D extends B{

    @Override
    public void m2(){

    }
}

