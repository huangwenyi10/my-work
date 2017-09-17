package com.example.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Created by Ay on 2017/9/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FiveDataTypeTest {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testStringType(){
        //String
        stringRedisTemplate.opsForValue().set("ay","ay");

    }


    @Test
    public void testHashType(){

        stringRedisTemplate.opsForHash().put("hashtest","1","1");
        stringRedisTemplate.opsForHash().put("hashtest","2","2");
        stringRedisTemplate.opsForHash().put("hashtest","3","3");

    }






}
