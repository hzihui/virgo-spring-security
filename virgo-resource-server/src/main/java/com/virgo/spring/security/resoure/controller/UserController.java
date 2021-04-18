package com.virgo.spring.security.resoure.controller;

import com.virgo.spring.security.resoure.domain.UserEntity;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HZI.HUI
 * @version 1.0
 * @since 2021/4/17
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @GetMapping("/list")
    public List<UserEntity> getUserList(){
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity().setId(1L).setName("HZI.HUI").setAge(18));

        return userEntityList;
    }
}
