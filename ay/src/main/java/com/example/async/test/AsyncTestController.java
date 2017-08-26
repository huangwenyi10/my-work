package com.example.async.test;

import com.example.intellij.test.AyTest;
import com.example.intellij.test.AyTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/async")
public class AsyncTestController {

    @Autowired
    private AsyncTestTask asyncTestTask;

    @RequestMapping("/test")
    public String test() throws Exception{
        long start = System.currentTimeMillis();
        Future<String> task1 = asyncTestTask.doTaskOne();
        Future<String> task2 = asyncTestTask.doTaskTwo();
        Future<String> task3 = asyncTestTask.doTaskThree();
        while(true){
            if(task1.isDone() && task2.isDone() && task3.isDone()){
                break;
            }else{
                Thread.sleep(1000);
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
        System.out.println("返回结果给前端");
        return "Hello Ay...";
    }

}
