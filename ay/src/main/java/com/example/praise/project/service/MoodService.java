package com.example.praise.project.service;

import com.example.praise.project.dto.MoodDTO;
import com.example.praise.project.dto.MoodPraiseRelDTO;
import com.example.praise.project.model.Mood;
import com.example.praise.project.model.MoodPraiseRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.example.praise.project.dao.MoodDao;

@Service
public class MoodService{

    @Resource
    private MoodDao moodDao;

    @Autowired
    private MoodPraiseRelService moodPraiseRelService;

    public int insert(Mood pojo){
        return moodDao.insert(pojo);
    }

    public int insertSelective(Mood pojo){
        return moodDao.insertSelective(pojo);
    }

    public int insertList(List<Mood> pojos){
        return moodDao.insertList(pojos);
    }

    public int update(Mood pojo){
        return moodDao.update(pojo);
    }

    public MoodDTO getMoodById(String id){
        MoodDTO mood = moodDao.getMoodById(id);
        //查询是否已经点赞了
        MoodPraiseRel moodPraiseRel = new MoodPraiseRel();
        moodPraiseRel.setMood_id(mood.getId());
        moodPraiseRel.setUser_id(mood.getUser_id());
        int count = moodPraiseRelService.findCountByMoodIdAndUserId(moodPraiseRel);
        //已经点赞
        if(count == 1){
            mood.setIs_praise("1");
        }

        return mood;
    }
}
