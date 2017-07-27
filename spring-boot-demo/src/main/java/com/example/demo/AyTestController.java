package com.example.demo;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@RequestMapping(value="/hello")
@EnableAutoConfiguration
@EnableScheduling
public class AyTestController {

    @RequestMapping("/ay")
    @ApiOperation(value = "ay",httpMethod ="GET", response = String.class,notes = "index")
    public String index(){
        return "Hello Ay...";
    }

    @RequestMapping("/test")
    @ApiOperation(value = "test",httpMethod ="GET", response = String.class,notes = "index")
    public String test(){
        return "Hello Ay...";
    }
}