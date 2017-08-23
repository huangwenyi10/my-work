package com.example.transaction.test;

import com.example.intellij.test.AyTest;
import com.example.intellij.test.AyTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/transaction")

public class TransactionTestController {


    @Autowired
    private AyTestService ayTestService;


    @RequestMapping("/ay")
    @Transactional
    public String index() {
        AyTest ayTest = new AyTest();
        ayTest.setId("1");
        ayTest.setName("ay");

        AyTest ayTest2 = new AyTest();
        ayTest2.setId("2");
        ayTest2.setName("al & ay");

        AyTest ayTest3 = new AyTest();
        ayTest3.setId("3");
        ayTest3.setName("al");

        ayTestService.insert(ayTest);
        ayTestService.insert(ayTest2);
        //这边出现异常
        String s = null;
        s.split(",");

        ayTestService.insert(ayTest3);
        return "Hello Ay...";
    }



}
