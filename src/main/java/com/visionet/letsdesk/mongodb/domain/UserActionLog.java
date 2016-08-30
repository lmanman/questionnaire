package com.visionet.letsdesk.mongodb.domain;


public class UserActionLog extends BaseMongoVo{
	protected static final long serialVersionUID = 1373760761780840082L;

	private String userName;
	private Long userId;
	private String url;
	private Long fkId;
	private String actionType;
	private String urlDesc;
	private String createDate;
	private String endDate;
	private Long orgId;
	private Long consumeTime;
	private String excptionMsg;
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getFkId() {
		return fkId;
	}
	public void setFkId(Long fkId) {
		this.fkId = fkId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getUrlDesc() {
		return urlDesc;
	}
	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Long consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getExcptionMsg() {
		return excptionMsg;
	}
	public void setExcptionMsg(String excptionMsg) {
		this.excptionMsg = excptionMsg;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Override
	public String toString() {
		return "ActionLog [url=" + url + ", userName=" + userName + ", createDate=" + createDate + "]";
	}

}

