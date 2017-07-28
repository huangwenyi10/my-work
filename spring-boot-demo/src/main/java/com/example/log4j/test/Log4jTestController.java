package com.example.log4j.test;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@RequestMapping(value="/log4j")
@EnableAutoConfiguration
@EnableScheduling
public class Log4jTestController {

    private static final Logger LOG = LoggerFactory.getLogger(Log4jTestController.class);

    @RequestMapping("/ay")
    @ApiOperation(value = "ay",httpMethod ="GET", response = String.class,notes = "index")
    public String index(){
        LOG.info("log4j is success");
        return "Hello Ay...";
    }

    @RequestMapping("/test")
    @ApiOperation(value = "test",httpMethod ="GET", response = String.class,notes = "index")
    public String test(){
        return "Hello Ay...";
    }
}