package com.example.praise.project.service;

import com.example.praise.project.model.MoodPraiseRel;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import com.example.praise.project.dao.MoodPraiseRelDao;

@Service
public class MoodPraiseRelService{

    @Resource
    private MoodPraiseRelDao moodPraiseRelDao;

    public int insert(MoodPraiseRel pojo){
        return moodPraiseRelDao.insert(pojo);
    }

    public int insertSelective(MoodPraiseRel pojo){
        return moodPraiseRelDao.insertSelective(pojo);
    }

    public int insertList(List<MoodPraiseRel> pojos){
        return moodPraiseRelDao.insertList(pojos);
    }

    public int update(MoodPraiseRel pojo){
        return moodPraiseRelDao.update(pojo);
    }
}
