package com.example.mybatis.test;

import java.util.ArrayList;
import java.util.List;
import com.example.mybatis.test.AppMessage;
import com.example.mybatis.test.AppMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AppMessageService implements IAppMessageService {

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Override
    public List<AppMessage> getMessage(){
        List<AppMessage> list = new ArrayList<AppMessage>();
        list.add(appMessageMapper.selectByPrimaryKey("xtt"));
        //list = mapper.selectAll();
        return list;
    }

    @Override
    public List<AppMessage> getAllMessage(){
        List<AppMessage> list = new ArrayList<AppMessage>();
        list = appMessageMapper.selectAll();
        return list;
    }

    @Override
    public int addMessage(AppMessage appMessage) {
        return appMessageMapper.insert(appMessage);
    }

    @Override
    public List<AppMessage> getMessageById(String id) {
        return appMessageMapper.getMessById(id);
    }

    @Override
    public int delMessage(String id) {
        return appMessageMapper.deleteByPrimaryKey(id);
    }



}