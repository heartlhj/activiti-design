/**
 *
 * 广州睿智信息科技有限公司, 版权所有 违者必究
 * copyright 2015-2020
 * @date 2016年1月12日 下午7:13:15
 * @author yuxiao
 * @Description: 分页查询时使用
 * 
 */
package com.lhj.activiti.design.model;

import java.io.Serializable;

/**
 *
 * @date 2016年1月12日 下午7:13:15
 * @author yuxiao
 * @Description: 分页查询时使用
 * 
 */
public class PageBean implements Serializable{

	private static final long serialVersionUID = -7792658878919011424L;
	
	// 分页开始位置
	private Integer start;
	// 分页结束位置
	private Integer end;
	// 每页多少行（页面传入）
	private Integer limit;
	// 当前第几页（页面传入）
	private Integer page;
	
	//排序字段
	private String sidx;
	
	//排序类型
	private String sord;
	
	// 偏移量（手机端）
	private Integer offset;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	/**
	 * 
	 * @date 2016年8月18日 下午8:35:58
	 * @author 杰
	 * @Description: 通过当前在第几页和每页几条，计算从第几条开始
	 * @return
	 *
	 */
	public Integer getStart() {
		
		if(this.page != null){
			this.start = (this.page -1 ) * this.limit;
		}
		
		//手机端
		if(this.offset != null){
			this.start = this.offset;
		}
		
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * 
	 * @date 2016年8月18日 下午8:35:10
	 * @author 杰
	 * @Description: 通过当前在第几页和每页几条，计算从第几条结束
	 * @return
	 *
	 */
	public Integer getEnd() {
		
		if(this.page != null){
			this.end = this.page * this.limit;
		}
		
		//手机端
		if(this.offset != null && this.limit != null){
			this.end = this.offset + this.limit;
		}
		
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}
	
	/**
	 * 
	 * @date 2017年7月21日 下午2:11:24
	 * @author Kevin
	 * @Description: 获取总页数
	 * @param count
	 * @return
	 *
	 */
	public int getTotalPage(int count){
		
		int totalPage = 0;
		
		if(limit != null && limit.intValue() == -1){
			totalPage = 1;
		}else{
			totalPage = count % limit == 0 ? count / limit : count / limit + 1;
		}
		
		return totalPage;
	}

	
}