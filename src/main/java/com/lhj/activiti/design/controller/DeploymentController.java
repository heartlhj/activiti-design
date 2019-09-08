package com.lhj.activiti.design.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lhj.activiti.design.dean.ActivitiModelDto;
import com.lhj.activiti.design.utils.InvoteUtil;
import com.lhj.activiti.design.utils.JsonUtils;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;


@Controller("deploymentController")
@RequestMapping(value = "deployment" )
public class DeploymentController extends BaseController{
    private static Logger LOG = LoggerFactory.getLogger(DeploymentController.class);
    private static final JsonUtils jsonUtils = JsonUtils.getInstance();
    @Autowired
    private RepositoryService repositoryService;


    /**
     * 查询部署的流程列表
     * @param ibean
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/func/deployment/pagin")
    @ResponseBody
    public String pagin(ActivitiModelDto ibean,HttpServletRequest request,HttpServletResponse response, Model model) {

        if(LOG.isDebugEnabled()){
            LOG.debug("请求参数  pagin getParameterMap:{}", jsonUtils.objectToJson(request.getParameterMap()));
        }

        //业务逻辑开始
        String json = null;
        List<ActivitiModelDto> list = new ArrayList<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        int count = (int)processDefinitionQuery.count();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(ibean.getStart(),ibean.getLimit());
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            ActivitiModelDto activitiModelDto = new ActivitiModelDto();
            copyProperties(deployment,activitiModelDto);
            activitiModelDto.setKey(processDefinition.getKey());
            activitiModelDto.setVersion(processDefinition.getVersion());
            activitiModelDto.setProcessDefinitionId(processDefinition.getId());
            list.add(activitiModelDto);
        }

        Integer page = ibean.getPage();

        int total = ibean.getTotalPage(count);

        json = this.toDataGridJson(page, total, count, list);

        //业务逻辑结束

        if(LOG.isDebugEnabled()){
            LOG.debug("输出参数json:{}", json);
        }
        return json;
    }

    @RequestMapping(value = "deleteDeployment")
    @ResponseBody
    public Object delete(Model model, ActivitiModelDto  activitiModelDto,
                         HttpServletRequest request,HttpServletResponse response) {
        String message = "删除成功";
        String[] deploymentIds = activitiModelDto.getDeploymentIds();
        String json = null;
        Map<String,String> map = InvoteUtil.initMap();
        if(deploymentIds != null && deploymentIds.length > 0 ){
            for (String deploymentId : deploymentIds) {
                repositoryService.deleteDeployment(deploymentId, true);
                map = InvoteUtil.setSuccessMap(map);
                json = jsonUtils.objectToJson(map);
            }
        }
        return json;
    }

    @RequestMapping(value = "convert-to-model")
    public String convertToModel( ActivitiModelDto  activitiModelDto)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(activitiModelDto.getProcessDefinitionId()).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
        org.activiti.engine.repository.Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return "redirect:/workflow/model/list";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
