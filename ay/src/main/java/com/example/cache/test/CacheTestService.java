package com.example.cache.test;

import com.example.springdata.test.AyTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ay on 2017/9/2.
 */
@Component
public class CacheTestService {

    @Autowired
    private UserCacheRepository userCacheRepository;

    public AyTest getAllUsers(String id){
        return userCacheRepository.findById(id);
    }
}
