package com.example.praise.project.controller;

import com.example.praise.project.model.Mood;
import com.example.praise.project.model.User;
import com.example.praise.project.service.MoodService;
import com.example.praise.project.service.UserService;
import com.example.praise.project.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
