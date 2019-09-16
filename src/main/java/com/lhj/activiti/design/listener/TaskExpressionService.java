package com.lhj.activiti.design.listener;

import com.lhj.activiti.design.service.UserService;
import com.lhj.activiti.design.service.impl.UserServiceImpl;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taskExpressionService")
public class TaskExpressionService implements TaskListener {

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateExecution) {
        String eventName = delegateExecution.getEventName();
        if ("create".endsWith(eventName)) {
            System.out.println("create=========");
        }else if ("assignment".endsWith(eventName)) {
            System.out.println("assignment========"+delegateExecution.getAssignee());
        }else if ("complete".endsWith(eventName)) {
            System.out.println("complete===========");
        }else if ("delete".endsWith(eventName)) {
            System.out.println("delete=============");
        }

    }
}