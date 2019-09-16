package com.lhj.activiti.design.controller;


import com.lhj.activiti.design.dean.Global;
import com.lhj.activiti.design.service.impl.UserServiceImpl;
import com.lhj.activiti.design.utils.JsonUtils;
import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    private static Logger LOG = LoggerFactory.getLogger(ActivitiController.class);
    private static final JsonUtils jsonUtils = JsonUtils.getInstance();

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model model){
        //业务逻辑开始
        ModelAndView pageView = new ModelAndView("login");
        return pageView;
    }

    @RequestMapping(value = "/")
    public String indexInt(HttpServletRequest request, HttpServletResponse response, Model model){
        //业务逻辑开始
        ModelAndView pageView = new ModelAndView("index");
        return "index";
    }

    @RequestMapping(value = "/login/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model,
                              @RequestParam("username") String userName, @RequestParam("password") String password,
                              HttpSession session){
        if (LOG.isDebugEnabled()) {
            LOG.debug("请求参数: intoPage getParameterMap:{}"+jsonUtils.objectToJson(request.getParameterMap()));
        }
        ModelAndView pageView = null;
        boolean checkPassword = identityService.checkPassword(userName, password);
        if (checkPassword) {
            // read user from database
            org.activiti.engine.identity.User user = identityService.createUserQuery().userId(userName).singleResult();
            request.getSession().setAttribute(Global.USER_SESSION_KEY, user);
            pageView = new ModelAndView("redirect:/main.do");
        }else{
            pageView = new ModelAndView("redirect:/index.do");
        }
        return pageView;
    }
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute(Global.USER_SESSION_KEY);
        //业务逻辑开始
        ModelAndView pageView = new ModelAndView("redirect:/index.do");
        return pageView;
    }

    @RequestMapping(value = "/main")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response, Model model,
                              String userAccount,String userPassword,String remember){
        ModelAndView pageView = new ModelAndView("main");
        return pageView;


    }
}
