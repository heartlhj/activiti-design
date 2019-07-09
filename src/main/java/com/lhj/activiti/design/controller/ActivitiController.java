package com.lhj.activiti.design.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lhj.activiti.design.dean.ActivitiModelDto;
import com.lhj.activiti.design.utils.InvoteUtil;
import com.lhj.activiti.design.utils.JsonUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;


@Controller("activitiController")
@RequestMapping(value = "activiti" )
public class ActivitiController extends BaseController{
    private static Logger LOG = LoggerFactory.getLogger(ActivitiController.class);
    private static final JsonUtils jsonUtils = JsonUtils.getInstance();
    @Autowired
    private RepositoryService repositoryService;



    @RequestMapping(value = "/funcPage/model/create")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("请求参数: intoPage getParameterMap:{}"+jsonUtils.objectToJson(request.getParameterMap()));
        }
        doBeforeMenuPageAction(request, response, model, null);
        ModelAndView pageView = new ModelAndView("model/create");
        doAfterMenuPageAction(request, response, model, null);
        return pageView;
    }

    @RequestMapping(value = "/funcPage/model/import")
    public ModelAndView imports(HttpServletRequest request,HttpServletResponse response, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("请求参数: intoPage getParameterMap:{}"+jsonUtils.objectToJson(request.getParameterMap()));
        }
        doBeforeMenuPageAction(request, response, model, null);
        ModelAndView pageView = new ModelAndView("model/import");
        doAfterMenuPageAction(request, response, model, null);
        return pageView;
    }

    @RequestMapping(value = "/funcPage/model/copy")
    public ModelAndView copy(HttpServletRequest request,HttpServletResponse response, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("请求参数: intoPage getParameterMap:{}"+jsonUtils.objectToJson(request.getParameterMap()));
        }
        doBeforeMenuPageAction(request, response, model, null);
        ModelAndView pageView = new ModelAndView("model/copy");
        doAfterMenuPageAction(request, response, model, null);
        return pageView;
    }

    @RequestMapping(
            value = {"createModel"},
            method = {RequestMethod.POST}
    )
    public void createModel(@RequestParam("name") String name, @RequestParam("description") String description,
              HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            org.activiti.engine.repository.Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(name));
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "deleteModel")
    @ResponseBody
    public Object delete(Model model, ActivitiModelDto  activitiModelDto,
                         HttpServletRequest request,HttpServletResponse response) {
        String message = "删除成功";
        String[] modelIds = activitiModelDto.getModelIds();
        String json = null;
        Map<String,String> map = InvoteUtil.initMap();
        if(modelIds != null && modelIds.length > 0 ){
            for (String modelId : modelIds) {
                repositoryService.deleteModel(modelId);
                map = InvoteUtil.setSuccessMap(map);
                json = jsonUtils.objectToJson(map);
            }
        }
        return json;
    }

    @RequestMapping(value = "import")
    public void deployementProcessDefinitionByString(MultipartFile file){
        try {
            String message = "导入成功";
//            Model modelData = repositoryService.getModel(modelId);
//            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
//            byte[] bpmnBytes = null;
//            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
//            bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            InputStream inputStream = null;
            inputStream = file.getInputStream();
            InputStream inputStream1 = file.getInputStream();

            BpmnXMLConverter converter = new BpmnXMLConverter();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader1 = factory.createXMLStreamReader(inputStream1);//createXmlStreamReader
            //将xml文件转换成BpmnModel
            BpmnModel bpmnModel = converter.convertToBpmnModel(reader1);
            List<Process> processes = bpmnModel.getProcesses();
            String name = "";
            for (Process process : processes) {
                name = process.getName();
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            ObjectNode jsonNodes = new BpmnJsonConverter().convertToJson(bpmnModel);
//            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            ObjectMapper objectMapper = new ObjectMapper();
            String description = name;
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            org.activiti.engine.repository.Model modelData = repositoryService.newModel();
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(name));
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), jsonNodes.toString().getBytes("utf-8"));

//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            inputStream.close();
//            String text = sb.toString();
//            System.out.println(name);
//            Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service  
//                    .createDeployment()//创建部署对象  
//                    .addString(name, text)
//                    .deploy();//完成部署  
//            System.out.println("部署ID：" + deployment.getId());//1  
//            System.out.println("部署时间：" + deployment.getDeploymentTime());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导入失败");
        }
    }

    /**
     * 根据Model部署流程
     */
    @RequestMapping(value = "deploy")
    @ResponseBody
    public Object delete(ActivitiModelDto  activitiModelDto, Model models,
                         HttpServletRequest request,HttpServletResponse response) {
        Map<String,String> map = InvoteUtil.initMap();
        String json = null;
        try {
            String message = "部署成功";
            org.activiti.engine.repository.Model modelData = repositoryService.getModel(activitiModelDto.getId());
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
            map = InvoteUtil.setSuccessMap(map);
            json = jsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("根据模型部署流程失败：modelId={}", activitiModelDto.getId(), e);
            map = InvoteUtil.initMap();
            json = jsonUtils.objectToJson(map);
        }
        return json;
    }

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


        } catch (Exception e) {
            LOG.error("导出model的xml文件失败：modelId={}, type={}", modelId, e);
        }
    }


    @RequestMapping(value = "model/copy",method = {RequestMethod.POST})
    public void copy(ActivitiModelDto d, Model models){
        try {
            String message = "复制成功";
            org.activiti.engine.repository.Model modelData = repositoryService.getModel(d.getId());
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);

            ObjectNode jsonNodes = new BpmnJsonConverter().convertToJson(model);
            ObjectMapper objectMapper = new ObjectMapper();
            String description = d.getDescriptionCopy();
            String name = d.getNameCopy();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            org.activiti.engine.repository.Model modelDataNow = repositoryService.newModel();
            modelDataNow.setMetaInfo(modelObjectNode.toString());
            modelDataNow.setName(name);
            modelDataNow.setKey(StringUtils.defaultString(name));
            repositoryService.saveModel(modelDataNow);
            repositoryService.addModelEditorSource(modelDataNow.getId(), jsonNodes.toString().getBytes("utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("复制失败");
        }
    }

    @RequestMapping(value = "/func/model/pagin")
    @ResponseBody
    public String pagin(ActivitiModelDto ibean,HttpServletRequest request,HttpServletResponse response, Model model) {

        if(LOG.isDebugEnabled()){
            LOG.debug("请求参数  pagin getParameterMap:{}", jsonUtils.objectToJson(request.getParameterMap()));
        }

        //业务逻辑开始
        String json = null;
        Long count = repositoryService.createModelQuery().count();
        List<org.activiti.engine.repository.Model> listModel = repositoryService.createModelQuery().
                orderByLastUpdateTime().desc().
                listPage(ibean.getStart(),ibean.getLimit());
        List<ActivitiModelDto> list = new ArrayList<ActivitiModelDto>();
        for (org.activiti.engine.repository.Model models : listModel) {
            ActivitiModelDto activitiModelDto = new ActivitiModelDto();
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
