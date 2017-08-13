package com.example.demo;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/hello")
public class AyController {

    @RequestMapping("/ay")
    @ApiOperation(value = "第一个方法",httpMethod ="GET", response = String.class,notes = "重要的")
    public String index(){
        return "Hello Ay...";
    }

    @RequestMapping("/al")
    @ApiOperation(value = "第二个方法",httpMethod ="GET", response = String.class,notes = "不重要的")
    public String index2(){
        return "Hello Ay...";
    }
}
