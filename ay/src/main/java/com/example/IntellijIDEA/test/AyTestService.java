package com.example.IntellijIDEA.test;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.example.IntellijIDEA.test.AyTest;
import com.example.IntellijIDEA.test.AyTestDao;

@Service
public class AyTestService{

    @Resource
    private AyTestDao ayTestDao;

    public int insert(AyTest pojo){
        return ayTestDao.insert(pojo);
    }

    public int insertSelective(AyTest pojo){
        return ayTestDao.insertSelective(pojo);
    }

    public int insertList(List<AyTest> pojos){
        return ayTestDao.insertList(pojos);
    }

    public int update(AyTest pojo){
        return ayTestDao.update(pojo);
    }
}
