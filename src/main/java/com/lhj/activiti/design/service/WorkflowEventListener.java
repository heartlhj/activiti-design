package com.lhj.activiti.design.service;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @Description:  流程事件侦听
 * @ param
 * @return
 * @author 李海军
 * @date 2018/7/25  15:38
 */
@Service()
public class WorkflowEventListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowEventListener.class);


    static {
        logger.info("WorkflowEventListener init....................");
    }


    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical,
        // exceptions
        // can be ignored if logging fails...
        return false;
    }

    @Override
    public void onEvent(ActivitiEvent event) {
        logger.info("onEvent:{}", event.getType());

        switch (event.getType()) {
            case TASK_CREATED:// 任务创建
                logger.info("TASK_CREATED");
                this.taskCreate(event);
                break;
            case JOB_EXECUTION_SUCCESS:
                logger.debug("JOB_EXECUTION_SUCCESS");
                break;

            case JOB_EXECUTION_FAILURE:
                logger.info("JOB_EXECUTION_FAILURE");
                break;

            case PROCESS_COMPLETED:// 流程结束
                logger.info("PROCESS_COMPLETED");
                this.processComplete(event);
                break;
            default:
                logger.info("default");
        }
    }

    /*
     *
     * @Description: 任务创建
     * @ param [event]
     * @return void
     * @author 李海军
     * @date 2018/7/23  14:39
     */
    private void taskCreate(ActivitiEvent event) {
        ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
        TaskEntity taskEntity = (TaskEntity) eventImpl.getEntity();
        String processInstanceId = taskEntity.getProcessInstanceId();
        String taskId = taskEntity.getId();
        String taskDefinitionKey = taskEntity.getTaskDefinitionKey();
        Map<String, Object> map = taskEntity.getActivityInstanceVariables();
        for (String key : map.keySet()){
            if ("flowType".equals(key)){
                return;
            }
        }
        String busiOrderId = (String) map.get("BUS_ORDER_ID");
        logger.info("环节任务生成 taskCreate,processInstanceId:{},taskId:{},taskDefinitionKey:{},busiOrderId:{}", processInstanceId, taskId,taskDefinitionKey,busiOrderId);
        return;
    }

    /*
     *
     * @Description: 流程结束
     * @ param [event]
     * @return void
     * @author 李海军
     * @date 2018/7/23  14:40
     */
    private void processComplete(ActivitiEvent event) {
        ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
        String processInstanceId = eventImpl.getProcessInstanceId();
        ExecutionEntity entity = (ExecutionEntity)eventImpl.getEntity();

        Map<String, Object> map = entity.getVariables();
        String busiOrderId = (String) map.get("BUS_ORDER_ID");
        logger.info("流程实例完成 processComplete,processInstanceId:{},busiOrderId:{}", processInstanceId, busiOrderId);

    }

}
