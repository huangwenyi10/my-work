package com.example.dubbo.test;


import com.alibaba.dubbo.config.annotation.Service;

/**
 * Created by Ay on 2017/9/6.
 */
@Service(version = "1.0.0")
public class UserDubboServiceImpl implements UserDubboService {
    @Override
    public void getUserByName(String name) {
        System.out.println("success!!!");
    }
}

