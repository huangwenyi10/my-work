package com.example.demo.test;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Ay on 2017/9/3.
 */
@Component("rMIExService")
public class RMIExServiceImpl implements RMIExService{

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
