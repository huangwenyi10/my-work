package com.example.intellij.plugins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：调试功能
 * @author Ay
 * @date   2017/9/24.
 */
public class DebugTest implements Serializable{

    //alt + insert 快捷键 可以自动生成 serialVersionUID
    private static final long serialVersionUID = 428002838170755839L;

    public static void main(String[] args) {
        System.out.println("1");//alt + p  全部大写
        System.out.println("1");//alt + l  全部小写
        //alt + c 首字母大写
        test();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for(Integer i : list){
            System.out.println(i);
        }
    }

    public static void test(){
        System.out.println("1");
        System.out.println("1");
        System.out.println("1");
    }

}
