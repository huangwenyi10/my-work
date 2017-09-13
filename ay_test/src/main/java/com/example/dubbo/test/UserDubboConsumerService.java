package com.example.dubbo.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * Created by Ay on 2017/9/6.
 */
@Component
public class UserDubboConsumerService{

    @Reference(version = "1.0.0")
    UserDubboService userDubboService;

    public void getUserByName(String name){
        userDubboService.getUserByName(name);
        System.out.println("success");
    }
}

