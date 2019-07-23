package com.lhj.activiti.design.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhj.activiti.design.dean.Global;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 

public class LoginInterceptor implements HandlerInterceptor {
 
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Object user = request.getSession().getAttribute(Global.USER_SESSION_KEY);
    if (user == null) {
      System.out.println("尚未登录，跳到登录页面");
      response.sendRedirect("./index.do");
      return false;
    }
     
    return true;
  }
 
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    System.out.println("postHandle");
  }
 
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    System.out.println("afterCompletion");
  }
 
}