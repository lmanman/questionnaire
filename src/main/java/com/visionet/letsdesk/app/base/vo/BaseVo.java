package com.visionet.letsdesk.app.base.vo;

import com.visionet.letsdesk.app.common.utils.PageInfo;

import java.io.Serializable;

public abstract class BaseVo implements Serializable{
	protected static final long serialVersionUID = -1373760761780840081L;

	protected Long id;
	//分页信息
	private PageInfo pageInfo;
		
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	
}
