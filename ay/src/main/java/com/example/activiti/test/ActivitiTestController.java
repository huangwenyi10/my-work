package com.example.activiti.test;

import com.example.async.test.AsyncTestTask;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiTestController {

    @Autowired
    RuntimeService runtimeService;

    @RequestMapping("/test")
    public void test() throws Exception{
        long count = runtimeService.createProcessInstanceQuery().count();
        System.out.println(count);
        System.out.println("success");
    }

}
