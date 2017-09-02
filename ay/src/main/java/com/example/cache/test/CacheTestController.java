package com.example.cache.test;
import com.example.springdata.test.AyTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ay on 2017/1/24.
 */
@RestController
@RequestMapping("/cache")
public class CacheTestController {

    @Autowired
    private CacheTestService cacheTestService;

    @RequestMapping("/ay")
    public String index() {
        AyTest users = cacheTestService.getAllUsers("1");
        return "Hello Ay...";
    }


}
