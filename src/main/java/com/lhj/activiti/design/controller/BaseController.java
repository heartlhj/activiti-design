
package com.lhj.activiti.design.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhj.activiti.design.model.DealResult;
import com.lhj.activiti.design.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 所有控制器的基类
 */
public abstract class BaseController implements ApplicationContextAware{

	
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	
	@Autowired
	private ApplicationContext applicationContext;

	

	public static String DATE_FORMET = "yyyy-MM-dd HH:mm:ss";

	private static final JsonUtils jsonUtils = JsonUtils.getInstance();
	
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	
	/**
	 *
	 * @Description: (指定时间类型编辑器)
	 * @param binder
	 *
	 */
	@InitBinder    
    public void initBinder(WebDataBinder binder) {  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true)); 
     }
	
	/**
	 * 在控制器之前调用，暂时无用，用于后续扩展
	 * 用于菜单页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doBeforeMenuPageAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {

	}

	/**
	 * 暂时无用，用于后续扩展
	 * 用于菜单页面
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doAfterMenuPageAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {
		

	}

	/**
	 * 在控制器之前调用，暂时无用，用于后续扩展
	 * 用于功能
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doBeforeFuncAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {

	}

	/**
	 * 暂时无用，用于后续扩展
	 * 用于功能
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doAfterFuncAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {

		
	}

	/**
	 * 在控制器之前调用，暂时无用，用于后续扩展
	 * 用于功能页面或模态窗口
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doBeforeFuncPageAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {

	}

	/**
	 * 暂时无用，用于后续扩展
	 * 用于功能页面或模态窗口
	 * @param request
	 * @param response
	 * @param model
	 * @param paramObj
	 */
	protected void doAfterFuncPageAction(HttpServletRequest request, HttpServletResponse response, Model model,
			Object paramObj) {

		
	}

	/**
	 * 从请求对象中获取知道名称的参数值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getParameterFromRequest(HttpServletRequest request, String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterFromRequest name:{}", name);
		}
		String value = null;
		if (request.getParameter(name) != null) {
			value = request.getParameter(name).toString();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterFromRequest name:{},value:{}", name, value);
		}

		return value;
	}
	
	/**
	 * 将结果集转为dataGrid使用的json
	 * @param page 当前第几页
	 * @param total 一共多少页
	 * @param records 一共多少条记录 
	 * @param dataList 数据
	 * @return
	 */
	protected String toDataGridJson(Integer page, Integer total, Integer records, List<?> dataList) {
//		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMET).serializeNulls().create(); // 值为空时name:
		// ""，默认是没有这个name

		StringBuilder jsonStr = new StringBuilder("{");
		jsonStr.append("\"page\": ");
		jsonStr.append(page == null ? 1: page);
		jsonStr.append(", \"total\": ");
		jsonStr.append(total == null ? 1: total);
		jsonStr.append(", \"records\": ");
		jsonStr.append(records == null ? -1: records);
		jsonStr.append(", \"root\": ");
//		JsonUtils.toJsonString(obj)
		jsonStr.append(jsonUtils.objectToJson(dataList));
		jsonStr.append("}");

		return jsonStr.toString();

	}
	


	protected String toPageJson(String returnCode, String returnMsg, Object returnData) {
		String json = null;
		DealResult dealResult = new DealResult();
		dealResult.setReturnCode(returnCode);
		dealResult.setReturnMsg(returnMsg);
		dealResult.setReturnData(returnData);
		json = jsonUtils.objectToJson(dealResult);
		return json;
	}
}
