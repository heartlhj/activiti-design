package com.lhj.activiti.design.model;

import java.io.Serializable;

/**
 * 
 *
 * @date 2017年10月18日 下午3:37:08
 * @author jie
 * @Description: 处理结果
 *
 */
public class DealResult implements Serializable {

	private static final long serialVersionUID = -8782284388718728546L;
	
	private String returnCode;//操作返回码//1000成功，999失败
	private String returnMsg;//操作结果信息
	private Object returnData;//操作返回数据
	
	public static final String SUCCESS = "1000";//成功（用于增删改的成功返回提示）
	public static final String QRYSUCCESS = "1001";//成功（用于查询，一般前台页面不用提示）
	public static final String FAILURE = "999";//失败
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public Object getReturnData() {
		return returnData;
	}
	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}
	
}
