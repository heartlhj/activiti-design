
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

