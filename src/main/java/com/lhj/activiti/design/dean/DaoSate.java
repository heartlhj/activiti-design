/**
 *
 * 广州睿智信息科技有限公司, 版权所有 违者必究
 * copyright 2015-2020
 * @date 2017年9月18日 下午2:32:46
 * @author LGK
 * @Description: 返回状态
 * 
 */
package com.lhj.activiti.design.dean;

/**
 *
 * @Description: 返回状态
 * 
 */
public enum DaoSate {

	SUCCESS("success","1000"),
	FAILURE("failure","999");
	
	

	private String code;
	private String codeNumber;

	// 构造方法
	private DaoSate(String code,String codeNumber) {
		this.code = code;
		this.setCodeNumber(codeNumber);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
}

