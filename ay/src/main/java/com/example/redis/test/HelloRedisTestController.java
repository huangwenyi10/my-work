package com.example.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
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


    /**
     * 描述：数据类型为set测试
     * @return
     */
    @RequestMapping("/set")
    public String set(){

        stringRedisTemplate.opsForSet().add("123","1");
        stringRedisTemplate.opsForSet().add("123","2");
        stringRedisTemplate.opsForSet().add("123","3");
        stringRedisTemplate.opsForSet().add("123","4");
        stringRedisTemplate.opsForSet().add("123","5");
        stringRedisTemplate.opsForSet().add("123","6");

        System.out.println(redisTemplate.opsForSet().members("123"));
        return "Hello Ay...";
    }

    /**
     * 描述：数据类型为hashset测试
     * @return
     */
    @RequestMapping("/hashset")
    public String hashset(){
        stringRedisTemplate.opsForHash().put("myhashset","a","a");
        stringRedisTemplate.opsForHash().put("myhashset","b","b");
        stringRedisTemplate.opsForHash().put("myhashset","c","c");
        stringRedisTemplate.opsForHash().put("myhashset","d","d");
        return "Hello Ay...";
    }

}
