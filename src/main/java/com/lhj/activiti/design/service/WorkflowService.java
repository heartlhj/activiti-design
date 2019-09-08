package com.lhj.activiti.design.service;


import org.activiti.engine.runtime.ProcessInstance;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李海军
 * @Date: 2018/7/20 17:12
 * @Description:
 */
public interface WorkflowService {

    /**
     *
     * @Description: 启动流程
     * @ param [processId, variables, object]
     * @return org.activiti.engine.runtime.ProcessInstance
     * @author 李海军
     * @date 2018/7/20  17:43
     */
    ProcessInstance startWorkflow(String processId, Map<String, Object> variables, Object object);
    /**
     *
     * @Description: 查询流程变量数据
     * @ param [processInstanceId, var3]
     * @return T
     * @author 李海军
     * @date 2018/7/20  17:44
     */
    <T> T findTodoTasks(String processInstanceId, Class<T> var3);
    /**
     *
     * @Description: 流程审批
     * @ param [taskId, variables]
     * @return void
     * @author 李海军
     * @date 2018/7/20  17:44
     */
    void finishProcess(String taskId, Map<String, Object> variables);
    /**
     *
     * @Description: 回退到上一个环节
     * @ param [processInstanceId, taskId, paramMap]
     * @return void
     * @author 李海军
     * @date 2018/7/20  17:44
     */
    void taskRollBack(String processInstanceId, String taskId, Map<String, Object> paramMap);
    /*
     *
     * @Description: 查询上一个节点
     * @ param
     * @return
     * @author lhj
     * @date 2019/7/18 14:52
     */
    List<String> lastTask(String processInstanceId, String taskId);

    /**
     *
     * @Description: 流程跳转
     * @ param [processInstanceId, taskId, targetTaskDefinitionKey, paramMap]
     * @return void
     * @author 李海军
     * @date 2018/7/20  17:56
     */
    void processJump(String processInstanceId, String taskId, String targetTaskDefinitionKey, Map<String, Object> paramMap);
    /**
     *
     * @Description: 挂起一个流程实例
     * @ param [processInstanceId]
     * @return void
     * @author 李海军
     * @date 2018/7/20  17:56
     */
    void processSuspend(String processInstanceId);

    /**
     *
     * @Description: 根据任务Id查看流程实例
     * @ param [taskId]
     * @return org.activiti.engine.repository.ProcessDefinition
     * @author 李海军
     * @date 2018/7/30  16:06
     */
    InputStream getProcessDefinitionByTaskId(String taskId);

    /*
     *
     * @Description: 根据流程实例ID获取流程图 高亮
     * @ param 
     * @return 
     * @author 李海军
     * @date 2019/3/6 11:33
     */
    InputStream getProcessDefinitionByprocessInstanceId(String processInstanceId);
}
