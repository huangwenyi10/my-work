package com.example.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/10/3.
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/ay")
    public void test(){
        System.out.println("123");
    }

}
