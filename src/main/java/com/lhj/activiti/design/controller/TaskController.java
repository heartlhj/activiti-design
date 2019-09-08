package com.lhj.activiti.design.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lhj.activiti.design.dean.ActivitiModelDto;
import com.lhj.activiti.design.dean.TaskBean;
import com.lhj.activiti.design.service.WorkflowService;
import com.lhj.activiti.design.utils.InvoteUtil;
import com.lhj.activiti.design.utils.JsonUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;


@Controller("taskController")
@RequestMapping(value = "task" )
public class TaskController extends BaseController{
    private static Logger LOG = LoggerFactory.getLogger(TaskController.class);
    private static final JsonUtils jsonUtils = JsonUtils.getInstance();

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private TaskService taskService;


    @RequestMapping(value = "/funcPage/finish")
    public ModelAndView finishPage(HttpServletRequest request,HttpServletResponse response, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("请求参数: intoPage getParameterMap:{}"+jsonUtils.objectToJson(request.getParameterMap()));
        }
        doBeforeMenuPageAction(request, response, model, null);
        ModelAndView pageView = new ModelAndView("task/finish");
        doAfterMenuPageAction(request, response, model, null);
        return pageView;
    }

    @RequestMapping(value = "finish")
    @ResponseBody
    public Object finish( Model models,
                              HttpServletRequest request,HttpServletResponse response) {
        Map<String,String> map = InvoteUtil.initMap();
        String json = null;
        TaskBean taskBean = jsonUtils.jsonToObject(this.getParameterFromRequest(request, "params"), TaskBean.class);
        try {
            String message = "激活成功";
            if(taskBean!= null){
                workflowService.finishProcess(taskBean.getId(),taskBean.getParamMap());
                map = InvoteUtil.setSuccessMap(map);
                json = jsonUtils.objectToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("流程审批失败：taskId={}", taskBean.getId(), e);
            map = InvoteUtil.initMap();
            json = jsonUtils.objectToJson(map);
        }
        return json;
    }

    /*
     *
     * @Description: 激活流程
     * @ param 
     * @return 
     * @author lhj
     * @date 2019/7/11 9:06
     */
    @RequestMapping(value = "createTask")
    @ResponseBody
    public Object createTask( Model models,
                         HttpServletRequest request,HttpServletResponse response) {
        Map<String,String> map = InvoteUtil.initMap();
        String json = null;
        ActivitiModelDto activitiModelDto = jsonUtils.jsonToObject(this.getParameterFromRequest(request, "params"),
                ActivitiModelDto.class);
        try {
            String message = "激活成功";
            if(activitiModelDto.getKey()!= null){
                workflowService.startWorkflow(activitiModelDto.getKey(),
                        activitiModelDto.getParamMap(), null);
                map = InvoteUtil.setSuccessMap(map);
                json = jsonUtils.objectToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("根据模型部署流程失败：modelId={}", activitiModelDto.getId(), e);
            map = InvoteUtil.initMap();
            json = jsonUtils.objectToJson(map);
        }
        return json;
    }
    /*
     *
     * @Description: 模型导出
     * @ param 
     * @return 
     * @author lhj
     * @date 2019/7/11 9:06
     */
    @RequestMapping(value = "export",method = {RequestMethod.GET})
    public void export(@RequestParam(required = false) String modelId,
                       HttpServletRequest request,HttpServletResponse response) {
        try {
            org.activiti.engine.repository.Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export ");
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            exportBytes = xmlConverter.convertToXML(bpmnModel);

            filename = mainProcessId + ".bpmn20.xml";
            String agent = request.getHeader("USER-AGENT");    // 获取浏览器类型
            // Edge
            if (null != agent && -1 != agent.indexOf("Edge")) {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
                // Firefox
            } else if (null != agent && -1 != agent.indexOf("Firefox")) {
                filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                // Chrome或360
            } else if (null != agent && -1 != agent.indexOf("Chrome")) {
                filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            } else {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

//            response.flushBuffer();
            OutputStream out;
            out = response.getOutputStream();
            //写文件
            int b;
            while((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();
            LOG.info("导出model的xml文件成功：modelId={}, type={}", modelId);

        } catch (Exception e) {
            LOG.error("导出model的xml文件失败：modelId={}, type={}", modelId, e);
        }
    }

    /*
     *
     * @Description: 查看流程图
     * @ param 
     * @return 
     * @author lhj
     * @date 2019/7/11 9:06
     */
    @RequestMapping(value = "funcPage/viewPic", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String viewPic(String  processInstanceId,HttpServletRequest request, HttpServletResponse response){
        try {
            InputStream is = workflowService.getProcessDefinitionByprocessInstanceId(processInstanceId);
            response.setContentType("image/jpeg");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            int len = 0;
            int maxLen = 1024;
            byte[] b = new byte[1024];

            while ((len = is.read(b, 0, maxLen)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     *
     * @Description: 查询模型列表
     * @ param 
     * @return 
     * @author lhj
     * @date 2019/7/11 9:18
     */
    @RequestMapping(value = "/func/task/pagin")
    @ResponseBody
    public String pagin(ActivitiModelDto ibean,HttpServletRequest request,HttpServletResponse response, Model model) {

        if(LOG.isDebugEnabled()){
            LOG.debug("请求参数  pagin getParameterMap:{}", jsonUtils.objectToJson(request.getParameterMap()));
        }

        //业务逻辑开始
        String json = null;
        Long count = taskService.createTaskQuery().count();
        List<Task> tasks = taskService.createTaskQuery().orderByTaskCreateTime().desc().
                listPage(ibean.getStart(), ibean.getLimit());
        List<TaskBean> list = new ArrayList<TaskBean>();
        for (Task models : tasks) {
            TaskBean activitiModelDto = new TaskBean();
            copyProperties(models,activitiModelDto);
            list.add(activitiModelDto);
        }

        int records = 0 ;
        records = (int) repositoryService.createModelQuery().count();
        Integer page = ibean.getPage();

        int total = ibean.getTotalPage(records);

        json = this.toDataGridJson(page, total, records, list);

        //业务逻辑结束

        if(LOG.isDebugEnabled()){
            LOG.debug("输出参数json:{}", json);
        }
        return json;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
