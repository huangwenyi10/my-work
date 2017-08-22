package com.example.springdata.test;

import com.example.intellij.test.Ay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/springdata")
public class SpringDataController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/test")
    public String index() {
        AyTest ayTest = new AyTest();
        ayTest.setId("60");
        ayTest.setName("ay and al");
        //select * from ay_test where id = '60' and name = "al"
        List<AyTest> ayTestList =  userRepository.findByIdAndName("60","ay and al");
        return "Hello Ay...";
    }


}
