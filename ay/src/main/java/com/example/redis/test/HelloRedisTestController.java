package com.example.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Ay
 * @date   2017/1/24.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/redis")
public class HelloRedisTestController {

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/index")
    public String index(){
        // 保存字符串
        stringRedisTemplate.opsForValue().set("al", "al");
        String string = stringRedisTemplate.opsForValue().get("al");
        System.out.println(string);
        return "Hello Ay...";
    }
}
