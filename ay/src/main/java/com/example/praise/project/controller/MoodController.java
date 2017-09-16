package com.example.praise.project.controller;

import com.example.praise.project.model.Mood;
import com.example.praise.project.model.User;
import com.example.praise.project.service.MoodService;
import com.example.praise.project.service.UserService;
import com.example.praise.project.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 描述：用户控制层
 * @author Ay
 * @date 2017/1/24.
 */
@RestController
@RequestMapping("/mood")
public class MoodController {


    @Autowired
    private MoodService moodService;

    @RequestMapping(value = "/add")
    public String add() throws Exception{

        Mood mood = new Mood();
        mood.setId(UuidUtil.generateUUID());
        mood.setContent("Hello Ay，this is my first mood！");
        mood.setPraise_num(0);
        mood.setUser_id(UuidUtil.generateUUID());
        moodService.insert(mood);
        return "Hello Ay...";
    }

    @RequestMapping(value = "/getMoodList",method = RequestMethod.GET)
    public String getMoodList() throws Exception{

        System.out.println("11111");
        return "Hello Ay...";
    }

    @RequestMapping(value = "/getMoodById",method = RequestMethod.GET)
    public Mood getMoodById() throws Exception{

        return moodService.getMoodById("67ff4377040248409ff49eb2ab90203f");
    }

}
