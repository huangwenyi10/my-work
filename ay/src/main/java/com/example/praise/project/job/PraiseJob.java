package com.example.praise.project.job;

import com.example.praise.project.model.Mood;
import com.example.praise.project.model.MoodPraiseRel;
import com.example.praise.project.service.MoodPraiseRelService;
import com.example.praise.project.service.MoodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述：点赞定时器
 * @author Ay
 * @date   2017/9/17
 */
@Transactional
public class PraiseJob {


    private static final String PRAISE_HASH_KEY = "com.example.praise.project.key";

    Logger logger = LogManager.getLogger(PraiseJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MoodService moodService;

    @Autowired
    private MoodPraiseRelService moodPraiseRelService;


    /**
     * 定时将缓存中的点赞入库保存
     */
    public void run(){
        logger.info("start run :" + "time is :" + new Date());

        Set<String> moodIdSet = redisTemplate.opsForSet().members(PRAISE_HASH_KEY);
        Set<MoodPraiseRel> moodPraiseRelSet = null;
        Set<Mood> moodSet = null;
        Mood mood = null;

        if(null == moodIdSet || moodIdSet.size() <= 0) return;

        moodPraiseRelSet = new HashSet<>();
        moodSet = new HashSet<>();
        for(String moodId:moodIdSet){
            //存放所有的说说点赞关联实体
            moodPraiseRelSet.addAll(redisTemplate.opsForSet().members(moodId));
            mood = new Mood();
            mood.setId(moodId);
            mood.setPraise_num(redisTemplate.opsForSet().size(moodId));
            moodSet.add(mood);
        }
        //1.批量保存说说点赞关联set
        moodPraiseRelService.insertSet(moodPraiseRelSet);
        //2.批量更新说说实体
        moodService.batchUpdate(moodSet);
        //3.清除缓存
        for(String moodId:moodIdSet){
            redisTemplate.delete(moodId);
            logger.info("delete set :" + "moodId is :" + moodId);
        }
        redisTemplate.delete(PRAISE_HASH_KEY);
        logger.info("delete PRAISE_HASH_KEY");
        logger.info("end run :" + "time is :" + new Date());
    }


}
