package com.example.demo;

import com.example.IntellijIDEA.test.AyTest;
import com.example.IntellijIDEA.test.AyTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/hello")
public class AyController {

    @Autowired
    private AyTestService ayTestService;

    @RequestMapping("/ay")
    @ApiOperation(value = "第一个方都，法",httpMethod = "GET", response = String.class,notes = "重要的")
    public String index() {
        AyTest ayTest = new AyTest();
        ayTest.setId("123");
        ayTest.setName("al");
        ayTestService.insert(ayTest);
        return "Hello Ay...";
    }

    @RequestMapping("/al")
    @ApiOperation(value = "第二个方法",httpMethod = "GET", response = String.class, notes = "不重要的")
    public String index2(){
        return "Hello Ay...";
    }

    @RequestMapping("/love")
    @ResponseBody
    public AyTest index3(@RequestParam String name,@RequestParam String id){
        AyTest ayTest = new AyTest();
        ayTest.setId("001");
        ayTest.setName("ay");
        return ayTest;
    }

}
