package com.example.praise.project.controller;

import com.example.praise.project.dto.MoodDTO;
import com.example.praise.project.dto.MoodPraiseRelDTO;
import com.example.praise.project.model.Mood;
import com.example.praise.project.model.MoodPraiseRel;
import com.example.praise.project.model.User;
import com.example.praise.project.service.MoodPraiseRelService;
import com.example.praise.project.service.MoodService;
import com.example.praise.project.service.UserService;
import com.example.praise.project.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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

    @Autowired
    private MoodPraiseRelService moodPraiseRelService;


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

    /**
     * 描述：点赞功能(传统的，不能抗压，并且频繁的与数据库操作)
     * @param moodPraiseRelDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/praise",method = RequestMethod.POST ,consumes="application/json;charset=UTF-8")
    public int praise(@RequestBody MoodPraiseRelDTO moodPraiseRelDTO) throws Exception{
        return moodPraiseRelService.insert(moodPraiseRelDTO);
    }

    /**
     * 描述：点赞功能(Redis缓存实现)
     * @param moodPraiseRelDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/praiseForRedis",method = RequestMethod.POST ,consumes="application/json;charset=UTF-8")
    public long praiseForRedis(@RequestBody MoodPraiseRelDTO moodPraiseRelDTO) throws Exception{
        return moodPraiseRelService.insertForRedis(moodPraiseRelDTO);
    }


}
