package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@EnableAutoConfiguration
public class AyTestController {

    @RequestMapping("/hello")
    public String index(){
        return "Hello Ay...";
    }
}