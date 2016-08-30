package com.visionet.letsdesk.mongodb.domain;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Pagination {
	/** 每页显示条数 */  
    private Integer pageSize = 100;
  
    /** 当前页 */  
    private Integer currentPage = 0;  
  
    /** 总页数 */  
    private Integer totalPage = 1;  
  
    /** 查询到的总数据量 */  
    private Integer totalNumber = 0;  
    
    private DBObject sort = new BasicDBObject("createDate", -1);
  
    public Pagination(){
    }
    public Pagination(Integer currentPage,Integer totalPage,Integer totalNumber){
    	this.currentPage = currentPage;
    	this.totalPage = totalPage;
    	this.totalNumber = totalNumber;
    }

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if(currentPage<0){
			this.currentPage = 0;
		}else{
			this.currentPage = currentPage;
		}
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public DBObject getSort() {
		return sort;
	}
	public void setSort(DBObject sort) {
		this.sort = sort;
	}
	
	public Integer getSkip() {
		return (currentPage) * pageSize;
	}
    
}
