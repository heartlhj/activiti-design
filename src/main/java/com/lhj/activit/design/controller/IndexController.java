package com.lhj.activit.design.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("进入");
        return "Hello World";
    }
}
