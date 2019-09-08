package com.lhj.activiti.design.service.impl;

import com.lhj.activiti.design.service.WorkflowService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

/**
 * @Author: 李海军
 * @Date: 2018/7/19 10:03
 * @Description: 流程操作
 */
@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;

    /**
     * 启动流程
     *
     * @param
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false,rollbackFor=Exception.class)
    public ProcessInstance startWorkflow(String processId,Map<String, Object> variables,Object object) {
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            processInstance = runtimeService.startProcessInstanceByKey(processId,variables);
            String processInstanceId = processInstance.getId();
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
            if(task != null){
                //设置流程实例变量
                runtimeService.setVariable( task.getExecutionId(),"data",object);
            }

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstance;
    }

    /**
     *
     * @Description: 根据流程实例Id查询流程变量
     * @ param [userId]
     * @return java.lang.String
     * @author 李海军
     * @date 2018/7/19  17:41
     */
    @Override
    public <T> T  findTodoTasks(String processInstanceId, Class<T> var3) {
        // 根据当前的ID查询
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        //获取流程实例变量
        T data = runtimeService.getVariable(task.getExecutionId(), "data", var3);
        return data;
    }
    /**
     *
     * @Description: 审批流程 正常流转
     * @ param [taskId, variables]
     * @return void
     * @author 李海军
     * @date 2018/7/19  17:41
     */
    @Override
    public void finishProcess(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId,variables);
    }

    /**
     *
     * @Description: 流程回退
     * @ param [processInstanceId, taskId, paramMap]
     * @return void
     * @author 李海军
     * @date 2018/7/20  18:03
     */
    @Override
    public void taskRollBack(String processInstanceId, String taskId, Map<String, Object> paramMap){
        try {
//            Map<String, Object> variables;
            // 取得当前任务
            HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
                    .singleResult();
            // 取得流程实例
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(currTask.getProcessInstanceId()).singleResult();
            if (instance == null) {
                //流程结束
            }
            List<String> keys = new ArrayList<>();
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
            for (HistoricTaskInstance item : historicTaskInstances) {
                keys.add(item.getTaskDefinitionKey());
            }
//            paramMap = instance.getProcessVariables();
            // 取得流程定义
            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(currTask
                    .getProcessDefinitionId());

            if (definition == null) {

                //log.error("流程定义未找到");
                return ;
            }
            // 取得上一步活动
            ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                    .findActivity(currTask.getTaskDefinitionKey());
            List<PvmTransition> nextTransitionList = currActivity
                    .getIncomingTransitions();

            //判断上一节点是否在已经存在过的历史任务，否则当成退单
            boolean flag = true;
            for (PvmTransition nextTransition : nextTransitionList) {
                if(!keys.contains(nextTransition.getSource().getId())){
                    flag = false;
                }
            }
            if(!flag){
                processSuspend(processInstanceId);
                return ;
            }

            // 清除当前活动的出口
            List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
            List<PvmTransition> pvmTransitionList = currActivity
                    .getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitionList) {
                oriPvmTransitionList.add(pvmTransition);
            }
            pvmTransitionList.clear();

            // 建立新出口
            List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
            for (PvmTransition nextTransition : nextTransitionList) {
                PvmActivity nextActivity = nextTransition.getSource();
                ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                        .findActivity(nextActivity.getId());
                TransitionImpl newTransition = currActivity
                        .createOutgoingTransition();
                newTransition.setDestination(nextActivityImpl);
                newTransitions.add(newTransition);
            }
            // 完成任务
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(instance.getId())
                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
            for (Task task : tasks) {
                taskService.complete(task.getId(), paramMap);
                historyService.deleteHistoricTaskInstance(task.getId());
            }
            // 恢复方向
            for (TransitionImpl transitionImpl : newTransitions) {
                currActivity.getOutgoingTransitions().remove(transitionImpl);
            }
            for (PvmTransition pvmTransition : oriPvmTransitionList) {
                pvmTransitionList.add(pvmTransition);
            }
            return ;
        } catch (Exception e) {
            throw new RuntimeException("流程回退失败:流程回退失败taskId:" + taskId, e);


        }
    }

    private void iteratorNextOutgoingTransitions(List<PvmTransition> oriPvmTransitionList, PvmTransition pvmTransitions, ProcessDefinitionEntity definition) {
        // 取得上一步活动
        // 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(pvmTransitions.getDestination().getId());
        List<PvmTransition> pvmTransitionList = currActivity
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            String type = (String)pvmTransition.getDestination().getProperty("type");
            if("exclusiveGateway".equals(type)){
                this.iteratorNextOutgoingTransitions(oriPvmTransitionList,pvmTransition,definition);
            }else{
                oriPvmTransitionList.add(pvmTransition);
            }
        }
    }

    /*
     *
     * @Description: 查询上一个节点
     * @ param
     * @return
     * @author lhj
     * @date 2019/7/18 14:52
     */
    @Override
    public List<String> lastTask(String processInstanceId, String taskId){
        // 取得当前任务
        HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(currTask
                .getProcessDefinitionId());

        if (definition == null) {

            return null;
        }
        // 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(currTask.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity
                .getIncomingTransitions();
        PvmActivity ac = null;
        List<String> keys = new ArrayList<>();
        for (PvmTransition nextTransition : nextTransitionList) {
            String conditionText = (String)nextTransition.getProperty("conditionText");
            if(StringUtils.isNotEmpty(conditionText)){
                this.iteratorFindLastTask(null,keys,nextTransition,definition,null);
            }else{
                String key = nextTransition.getSource().getId();
                keys.add(key);
            }

        }
        return  keys;
    }

    private void iteratorFindLastTask( List<TransitionImpl> newTransitions ,List<String> keys, PvmTransition nextTransitions,
                                       ProcessDefinitionEntity definition,Map<String, Object> paramMap) {
        // 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(nextTransitions.getSource().getId());
        List<PvmTransition> nextTransitionList = currActivity
                .getIncomingTransitions();
        for (PvmTransition nextTransition : nextTransitionList) {
            String conditionText = (String)nextTransition.getProperty("conditionText");
            if(StringUtils.isNotEmpty(conditionText)){
                String id = nextTransition.getSource().getId();
                this.checkVariable(paramMap,id);
                this.iteratorFindLastTask(null,keys,nextTransition,definition,paramMap);
            }else{
                if(keys!= null){
                    String key = nextTransition.getSource().getId();
                    keys.add(key);
                }
                if(newTransitions!= null){
                    PvmActivity nextActivity = nextTransition.getSource();
                    ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                            .findActivity(nextActivity.getId());
                    TransitionImpl newTransition = currActivity
                            .createOutgoingTransition();
                    newTransition.setDestination(nextActivityImpl);
                    newTransitions.add(newTransition);
                }
            }

        }
    }

    /**
     *
     * @Description: 流程跳转
     * @ param [processInstanceId, taskId, targetTaskDefinitionKey, paramMap]
     * @return void
     * @author 李海军
     * @date 2018/7/20  18:03
     */
    @Override
    public void processJump(String processInstanceId, String taskId, String targetTaskDefinitionKey, Map<String, Object> paramMap){
        try {
            if(null == paramMap){
                paramMap = new HashMap<>();
            }
            this.setParamMap(processInstanceId,taskId,paramMap);
//            Map<String, Object> variables;
            // 取得当前任务
            HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            // 取得流程实例
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            if (instance == null) {
                //流程结束
            }
//            paramMap = instance.getProcessVariables();
            // 取得流程定义

            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (repositoryService.getProcessDefinition(instance.getProcessDefinitionId()));

            if (definition == null) {
                //log.error("流程定义未找到");
                return ;
            }
            ActivityImpl currActivity = ((ProcessDefinitionImpl) definition).findActivity(currTask.getTaskDefinitionKey());//当前节点
            ActivityImpl nextActivity = ((ProcessDefinitionImpl) definition) .findActivity(targetTaskDefinitionKey);//目标节点


            // 清除当前活动的出口
            List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
            List<PvmTransition> pvmTransitionList = currActivity
                    .getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitionList) {
                oriPvmTransitionList.add(pvmTransition);
            }
            pvmTransitionList.clear();

            //建立新的线条
            TransitionImpl newTransition = currActivity.createOutgoingTransition();
            newTransition.setDestination(nextActivity);


            // 完成任务
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(instance.getId())
                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
            for (Task task : tasks) {
                taskService.complete(task.getId(), paramMap);
                historyService.deleteHistoricTaskInstance(task.getId());
            }
            // 恢复方向
            currActivity.getOutgoingTransitions().remove(newTransition);

            for (PvmTransition pvmTransition : oriPvmTransitionList) {
                pvmTransitionList.add(pvmTransition);
            }
            return ;
        } catch (Exception e) {
            throw new RuntimeException("流程跳转失败taskId:" + taskId, e);
        }
    }

    private void setParamMap(String processInstanceId, String taskId, Map<String, Object> paramMap) {
        // 取得当前任务
        HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(currTask
                .getProcessDefinitionId());

        if (definition == null) {
            return;
        }
        // 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(currTask.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity
                .getIncomingTransitions();
        PvmActivity ac = null;
        List<String> keys = new ArrayList<>();
        for (PvmTransition nextTransition : nextTransitionList) {
            String conditionText = (String)nextTransition.getProperty("conditionText");
            if(StringUtils.isNotEmpty(conditionText)){
                String id = nextTransition.getSource().getId();
                this.checkVariable(paramMap,id);
                this.iteratorFindLastTask(null,keys,nextTransition,definition,paramMap);
            }

        }
    }

    private void checkVariable(Map<String, Object> paramMap, String id) {
        List<String> variables = new ArrayList<>();
        variables.add("ifCZ");variables.add("ifBus");variables.add("ifCZOne");
        variables.add("ifCZTwo");variables.add("ifZROne");variables.add("ifZRTwo");
        variables.add("ifZRThree"); variables.add("ifZRFour");
        if(variables.contains(id)){
            paramMap.put(id,"");
        }
    }

    /**
     *
     * @Description: 流程挂起
     * @ param [processInstanceId]
     * @return void
     * @author 李海军
     * @date 2018/7/20  18:04
     */
    @Override
    public void processSuspend(String processInstanceId){
        try {
            // 取得流程实例
//        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            //挂起一个流程实例
            runtimeService.suspendProcessInstanceById(processInstanceId);
        } catch (Exception e) {
            throw new RuntimeException("挂起一个流程实例异常 processInstanceId:" + processInstanceId,e);
        }
    }
    @Override
    public InputStream getProcessDefinitionByTaskId(String taskId) {
        // 1. 得到task
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 2. 通过task对象的流程实例ID获取流程定义对象
        ProcessDefinition pd = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        InputStream in = repositoryService.getResourceAsStream(pd.getDeploymentId(),pd.getDiagramResourceName());
        return in;
    }


    @Override
    public InputStream getProcessDefinitionByprocessInstanceId(String procInstId) {

        try {
            ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
            HistoryService historyService = processEngineConfiguration.getHistoryService();
            // 获取流程实例
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(procInstId).singleResult();
            // 获取流程图
            BpmnModel bpmnModel = processEngineConfiguration.getRepositoryService().getBpmnModel(processInstance.getProcessDefinitionId());
            Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) processEngineConfiguration.getRepositoryService()
                    .getProcessDefinition(processInstance.getProcessDefinitionId());
            // 获取流程里所有线路
            List<HistoricActivityInstance> highLightedActivitList = historyService
                    .createHistoricActivityInstanceQuery().processInstanceId(procInstId).list();

            List<HistoricActivityInstance> backHighLightedActivitList = new ArrayList<>();

            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(procInstId).orderByHistoricActivityInstanceId().asc().list();
            for (HistoricActivityInstance hisAct : highLightedActivitList) {
                for (int i = 0; i < historicActivityInstanceList.size(); i++) {
                    String actId = historicActivityInstanceList.get(i).getActivityId();
                    if (hisAct.getActivityId().equals(actId)) {
                        backHighLightedActivitList.add(hisAct);
                    }
                }
            }
            // 高亮环节id集合
            List<String> highLightedActivitis = new ArrayList<>();
            // 高亮线路id集合
            List<String> highLightedFlows = getHighLightedFlows(definitionEntity, backHighLightedActivitList);
            for (HistoricActivityInstance tempActivity : backHighLightedActivitList) {
                String activityId = tempActivity.getActivityId();
                highLightedActivitis.add(activityId);
            }
            // 中文显示的是口口口，设置字体就好了
            InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,
                    highLightedFlows, "宋体", "宋体", "宋体", null, 1.0);
            // 单独返回流程图，不高亮显示的话加一句【InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);】
            // 输出资源内容到相应对象
            // 设置成图片
            return imageStream;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i)
                    .getActivityId());
            // 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            // 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1)
                    .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }


    /*
     *
     * @Description: 获取当前流程的下一个节点
     * @ param 
     * @return 
     * @author 李海军
     * @date 2018/12/16 16:42
     */
    public  String getNextNode(String procInstanceId){
        // 取得流程实例
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(procInstanceId).singleResult();
        // 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
        String nextId = "";
        TaskDefinition taskDefinition = null;
        for (Task task : tasks) {
            // 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
            ProcessDefinitionEntity def = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task
                    .getProcessDefinitionId());
            List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
            // 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            String activitiId = execution.getActivityId();
            // 4、然后循环activitiList
            // 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
            for (ActivityImpl activityImpl : activitiList) {
                String id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    //获取下一个节点信息
                    taskDefinition = nextTaskDefinition(activityImpl, activityImpl.getId(), null, procInstanceId);
                    nextId = taskDefinition.getKey();
                    break;
                }
            }
        }
        return nextId;
    }

    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString, String processInstanceId){

        PvmActivity ac = null;

        Object s = null;

        //如果遍历节点为用户任务并且节点不是当前节点信息
        if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){
            //获取该节点下一个节点信息
            TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();
            return taskDefinition;
        }else{
            //获取节点所有流向线路信息
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for(PvmTransition tr : outTransitions){
                ac = tr.getDestination(); //获取线路的终点节点
                //如果流向线路为排他网关
                if("exclusiveGateway".equals(ac.getProperty("type"))){
                    outTransitionsTemp = ac.getOutgoingTransitions();

                    //如果网关路线判断条件为空信息
                    if(StringUtils.isEmpty(elString)) {
                        //获取流程启动时设置的网关判断条件信息
                        elString = getGatewayCondition(ac.getId(), processInstanceId);
//                        elString = "2";
                    }

                    //如果排他网关只有一条线路信息
                    if(outTransitionsTemp.size() == 1){
                        return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, elString, processInstanceId);
                    }else if(outTransitionsTemp.size() > 1){  //如果排他网关有多条线路信息
                        for(PvmTransition tr1 : outTransitionsTemp){
                            s = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息
                            //判断el表达式是否成立
                            if(isCondition(ac.getId(), StringUtils.trim(s.toString()), elString)){
                                return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, elString, processInstanceId);
                            }
                        }
                    }
                }else if("userTask".equals(ac.getProperty("type"))){
                    return ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();
                }else{
                }
            }
            return null;
        }
    }

    /**
     * 查询流程启动时设置排他网关判断条件信息
     * @param  gatewayId          排他网关Id信息, 流程启动时设置网关路线判断条件key为网关Id信息
     * @param  processInstanceId  流程实例Id信息
     * @return
     */
    public String getGatewayCondition(String gatewayId, String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
        if(execution != null){
            Object variable = runtimeService.getVariable(execution.getId(), gatewayId);
            if(variable != null)
            return variable.toString();
        }
        return null;
    }

    /**
     * 根据key和value判断el表达式是否通过信息
     * @param  key    el表达式key信息
     * @param  el     el表达式信息
     * @param  value  el表达式传入值信息
     * @return
     */
    public boolean isCondition(String key, String el, String value) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable(key, factory.createValueExpression(value, String.class));
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
        return (Boolean) e.getValue(context);
    }
}
