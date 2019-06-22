package com.lhj.activiti.design.service.impl;

import com.lhj.activiti.design.dao.UserMapper;
import com.lhj.activiti.design.model.User;
import com.lhj.activiti.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    public int insert(User record){
        return  userMapper.insert(record);
    }

    public int insertSelective(User record){
        return userMapper.insert(record);
    }
}
