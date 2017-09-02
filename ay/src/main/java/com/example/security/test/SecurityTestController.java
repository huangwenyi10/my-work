package com.example.security.test;

import com.example.intellij.test.AyTest;
import com.example.intellij.test.AyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
public class SecurityTestController {

    @RequestMapping("/home")
    public String index() {
        return "Hello Ay...";
    }

}
