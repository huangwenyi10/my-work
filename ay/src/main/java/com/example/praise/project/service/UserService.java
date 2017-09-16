package com.example.praise.project.service;

import com.example.praise.project.dao.UserDao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.example.praise.project.model.User;

@Service
public class UserService{

    @Resource
    private UserDao userDao;

    public int insert(User pojo){
        return userDao.insert(pojo);
    }

    public int insertSelective(User pojo){
        return userDao.insertSelective(pojo);
    }

    public int insertList(List<User> pojos){
        return userDao.insertList(pojos);
    }

    public int update(User pojo){
        return userDao.update(pojo);
    }
}
