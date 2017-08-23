package com.example.mongodb.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ay on 2017/8/23.
 */
@Component
public class UserDaoImpl {


    @Autowired
    private MongoTemplate mongoTemplate;

    public List<AyTest> getAll(){
        mongoTemplate.findById("1",AyTest.class);
        return mongoTemplate.findAll(null);
    }

}
