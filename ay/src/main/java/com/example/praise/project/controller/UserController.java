package com.example.praise.project.controller;

import com.example.praise.project.model.User;
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
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add")
    public String add() throws Exception{
        User user = new User();
        user.setId(UuidUtil.generateUUID());
        user.setName("阿毅");
        user.setAccount("ay");
        userService.insert(user);
        return "Hello Ay...";
    }

}
