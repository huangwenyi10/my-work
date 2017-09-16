package com.example.praise.project.service;

import com.example.praise.project.model.Mood;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.example.praise.project.dao.MoodDao;

@Service
public class MoodService{

    @Resource
    private MoodDao moodDao;

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

    public Mood getMoodById(String id){
        return moodDao.getMoodById(id);
    }
}
