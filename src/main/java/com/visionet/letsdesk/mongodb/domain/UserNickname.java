package com.visionet.letsdesk.mongodb.domain;

public class UserNickname extends BaseMongoVo {
	protected static final long serialVersionUID = 1373760761780840083L;
	
	private Long userId;
	private Long nickuserId;
	private String nickuserName;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getNickuserId() {
		return nickuserId;
	}
	public void setNickuserId(Long nickuserId) {
		this.nickuserId = nickuserId;
	}
	public String getNickuserName() {
		return nickuserName;
	}
	public void setNickuserName(String nickuserName) {
		this.nickuserName = nickuserName;
	}
	
	
	

}
