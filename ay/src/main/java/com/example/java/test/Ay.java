package com.example.java.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ay on 2017/8/24.
 */
public class Ay {

    static Logger logger = LoggerFactory.getLogger(Ay.class);

    public static void main(String[] args) {

//        final String str1 = "c"; //final类型的变量在编译时当常量处理
//        String str2 = "ab" + "c";
//        String str3 = "ab" + str1;
//        System.out.println(str2==str3);
        //我是不一样的地方

        String str1 = "c";
        String str2 = "ab" + "c";
        String str3 = "ab" + str1;
        System.out.println(str2==str3);

        logger.debug("I am Debug ");
        logger.debug("");
    }
}
