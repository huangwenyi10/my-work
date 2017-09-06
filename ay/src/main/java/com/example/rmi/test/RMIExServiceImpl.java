package com.example.rmi.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Ay on 2017/9/3.
 */
@Component("rMIExService")
public class RMIExServiceImpl implements RMIExService{


    @Override
    public String sayHello() {
        return "hello ay";
    }

    @PostConstruct
    public void initMethod(){
        System.out.println("我是初始化方法时调用的");
    }

    @Override
    public String invokingRemoteService() {
        // TODO Auto-generated method stub
        String result="欢迎你调用远程接口";
        return result;
    }
}
