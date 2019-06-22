package com.lhj.activiti.design.controller;


import com.lhj.activiti.design.model.User;
import com.lhj.activiti.design.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("进入");
        User user = new User();
        user.setId("111");
        user.setName("45");
        user.setAge(11);
        userService.insert(user);
        //业务逻辑开始
        ModelAndView pageView = new ModelAndView("login");


        return pageView;
    }
}
