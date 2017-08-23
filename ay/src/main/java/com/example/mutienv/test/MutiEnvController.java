package com.example.mutienv.test;

import com.example.springdata.test.AyTest;
import com.example.springdata.test.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/mutienv")
public class MutiEnvController {


    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/ay")
    public String index() {

        Iterable<AyTest> ayTestList = userRepository.findAll();
        return "Hello Ay...";
    }



}
