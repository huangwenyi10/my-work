package com.example.mongodb.test;

import com.example.intellij.test.AyTest;
import com.example.intellij.test.AyTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/mongo")
public class MongoDBTestController {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @RequestMapping("/al")
    public String index2(){
        userDaoImpl.save();
        return "Hello Ay...";
    }


}
