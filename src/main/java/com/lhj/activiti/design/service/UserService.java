package com.lhj.activiti.design.service;

import com.lhj.activiti.design.model.User;

public interface UserService {

    int insert(User record);

    int insertSelective(User record);
}
