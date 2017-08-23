package com.example.log4j2.test;

import com.example.intellij.test.AyTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/log4j2")
public class Log4JTestController {


    @Autowired
    private AyTestService ayTestService;

    Logger logger = LogManager.getLogger(this.getClass());



    @RequestMapping("/al")

    public String index2(){
        logger.info("method start");
        logger.equals("method start");
        return "Hello Ay...";
    }



}
