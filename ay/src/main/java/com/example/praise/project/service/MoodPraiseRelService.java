package com.example.praise.project.service;

import com.example.praise.project.dto.MoodPraiseRelDTO;
import com.example.praise.project.model.Mood;
import com.example.praise.project.model.MoodPraiseRel;
import com.example.praise.project.util.UuidUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

import com.example.praise.project.dao.MoodPraiseRelDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoodPraiseRelService{

    Logger logger = LogManager.getLogger(MoodPraiseRelService.class);

    @Resource
    private MoodPraiseRelDao moodPraiseRelDao;

    @Autowired
    private MoodService moodService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    public long insert(MoodPraiseRel pojo){
        //1.保存到关联表中
        pojo.setId(UuidUtil.generateUUID());
        pojo.setPraise_time(new Date());
        moodPraiseRelDao.insert(pojo);
        //2.更新说说表中的点赞数量
        long count = moodPraiseRelDao.findCountByMoodId(pojo);
        Mood mood = new Mood();
        mood.setId(pojo.getMood_id());
        mood.setPraise_num(count);
        moodService.update(mood);
        return count;
    }

    /**
     * 描述：查询列表
     * @param pojo
     * @return
     */
    public List<MoodPraiseRelDTO> findByMoodIdAndUserId(MoodPraiseRel pojo){
        return moodPraiseRelDao.findByMoodIdAndUserId(pojo);
    }

    /**
     * 描述：通过说说id和用户id查询数量
     * @param pojo
     * @return
     */
    public int findCountByMoodIdAndUserId(MoodPraiseRel pojo){
        return moodPraiseRelDao.findCountByMoodIdAndUserId(pojo);
    }

    /**
     *  描述：通过说说id查询数量
     * @param pojo
     * @return
     */
    public int findCountByMoodId(@Param("pojo") MoodPraiseRel pojo){
        return moodPraiseRelDao.findCountByMoodId(pojo);
    }


    private static final String PRAISE_HASH_KEY = "com.example.praise.project.key";

    public long insertForRedis(MoodPraiseRel pojo){

        //设置点赞时间
        pojo.setPraise_time(new Date());
        pojo.setId(UuidUtil.generateUUID());

        //1.存放到hashmap中
        redisTemplate.opsForSet().add(PRAISE_HASH_KEY , pojo.getMood_id());
        //2.存放到set中
        redisTemplate.opsForSet().add(pojo.getMood_id(),pojo);

        //计算说说的点赞数量
        long count = redisTemplate.opsForSet().size(pojo.getMood_id());
        logger.info("mood_id is :" + pojo.getMood_id() + "and praise num is" + count);
        return count;
    }



    public int insertSelective(MoodPraiseRel pojo){
        return moodPraiseRelDao.insertSelective(pojo);
    }

    public int insertList(List<MoodPraiseRel> pojos){
        return moodPraiseRelDao.insertList(pojos);
    }

    public int insertSet(Set<MoodPraiseRel> pojos){
        return moodPraiseRelDao.insertSet(pojos);
    }

    public int update(MoodPraiseRel pojo){
        return moodPraiseRelDao.update(pojo);
    }
}
